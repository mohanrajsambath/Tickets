package com.ganesh.tickets.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ganesh.tickets.view_model.TicketListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TicketListViewModel::class)
    abstract fun bindUserViewModel(userViewModel: TicketListViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
