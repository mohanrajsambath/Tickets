package com.ganesh.tickets.di

import android.app.Application
import androidx.room.Room
import com.ganesh.tickets.data.local.TicketDao
import com.ganesh.tickets.data.local.TicketDataBase
import com.ganesh.tickets.data.remote.HttpApi
import com.ganesh.tickets.data.repository.Repository
import com.ganesh.tickets.data.repository.RepositoryImpl
import com.ganesh.tickets.util.InternetConnection
import com.ganesh.tickets.util.NetworkConnectionInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    internal fun provideInternetConnection(app: Application): InternetConnection {
        return InternetConnection(app)
    }

    @Singleton
    @Provides
    internal fun provideNetworkConnectionInterceptor(internet: InternetConnection): NetworkConnectionInterceptor {
        return NetworkConnectionInterceptor(internet)
    }

    @Singleton
    @Provides
    internal fun provideRepo(
        ticketDao: TicketDao,
        httpApi: HttpApi,
        internet: InternetConnection
    ): RepositoryImpl {
        return Repository(ticketDao, httpApi, internet)
    }

    @Singleton
    @Provides
    fun createHttpClient(networkInterceptor: NetworkConnectionInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(logging)
            .addInterceptor(networkInterceptor)
        client.readTimeout(5 * 60, TimeUnit.SECONDS)
        return client.addInterceptor {
            val original = it.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            val request = requestBuilder.method(original.method, original.body).build()
            return@addInterceptor it.proceed(request)
        }.build()

    }

    @Singleton
    @Provides
    fun provideGithubService(okHttpClient: OkHttpClient): HttpApi {
        return Retrofit.Builder()
            .baseUrl("https://private-14c693-rentapanda.apiary-mock.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

            .client(okHttpClient)

            .build()
            .create(HttpApi::class.java)
    }

    @Singleton
    @Provides
    fun provideTvMazeDatabase(app: Application): TicketDataBase {

        return Room.databaseBuilder(
            app,
            TicketDataBase::class.java, TicketDataBase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()


    }


    @Singleton
    @Provides
    fun providePersonDao(db: TicketDataBase): TicketDao {
        return db.ticketEntities()
    }


}
