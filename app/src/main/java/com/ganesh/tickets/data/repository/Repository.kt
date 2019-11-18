package com.ganesh.tickets.data.repository

import com.ganesh.tickets.data.local.TicketDao
import com.ganesh.tickets.data.remote.HttpApi
import com.ganesh.tickets.model.Resource
import com.ganesh.tickets.model.TicketModel
import com.ganesh.tickets.util.InternetConnection
import javax.inject.Inject

open class Repository
@Inject constructor(
    private val ticketDao: TicketDao,
    private val httpApi: HttpApi,
    private var internetConnection: InternetConnection
) : RepositoryImpl {

    override suspend fun getTicketDetails(): Resource<List<TicketModel>> {
        return try {
            // if no internet connection, fetch the values from local DB
            if (!internetConnection.isAvailable()) {
                val resultFromLocal = ticketDao.fetchTicketDetails()
                Resource.Success(resultFromLocal)
            } else {
                //get tickets list from the server
                val result = httpApi.getTicketDetailsAsync().await()
                //insert into the table, if a data is duplicate(based on order_id) then the data is replaced in the table
                ticketDao.insert(result)
                Resource.Success(result)
            }


        } catch (ex: Exception) {
            Resource.Error(ex)
        }
    }
}

interface RepositoryImpl {
    suspend fun getTicketDetails(): Resource<List<TicketModel>>
}