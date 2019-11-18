package com.ganesh.tickets.view_model


import androidx.lifecycle.MutableLiveData

import com.ganesh.tickets.data.repository.RepositoryImpl
import com.ganesh.tickets.model.Resource
import com.ganesh.tickets.model.TicketModel
import kotlinx.coroutines.*
import javax.inject.Inject


open class TicketListViewModel
@Inject constructor(private val repository: RepositoryImpl) : BaseViewModel() {

    private var ticketDetailsLiveData: MutableLiveData<List<TicketModel>> = MutableLiveData()
    private val canShowTicketDetails: MutableLiveData<Boolean> = MutableLiveData()
    private lateinit var selectedTicket: TicketModel

    /**
     *To get ticket details from repository
     */
    fun getTicketDetails() {
        canShowLoading.value = true

        launch {

            val result = withContext(Dispatchers.IO) {
                repository.getTicketDetails()
            }

            parseResponse(result)
        }

    }

     fun parseResponse(result:Resource<List<TicketModel>>){

        when (result) {

            is Resource.Success -> {
                ticketDetailsLiveData.value=result.data
            }

            is Resource.Error -> {
                errorMessage.value=result.exception.message
            }

        }

        canShowLoading.value = true
    }

    private fun showDetails() {
        canShowTicketDetails.value = true
    }

    /**
     * to get currently selected ticket item
     */
    fun getSelectedTicketDetails(): TicketModel {
        return selectedTicket
    }

    /**
     * on selecting an item
     */
    fun onTicketSelected(selectedTicket: TicketModel) {
        this.selectedTicket = selectedTicket
        showDetails()
    }

    fun getTicketListLiveData(): MutableLiveData<List<TicketModel>> {
        return ticketDetailsLiveData
    }

    fun getCanSHowTicketDetails(): MutableLiveData<Boolean> {
        return canShowTicketDetails
    }


}