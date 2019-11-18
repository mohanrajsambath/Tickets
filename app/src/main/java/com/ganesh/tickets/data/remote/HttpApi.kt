package com.ganesh.tickets.data.remote

import com.ganesh.tickets.model.TicketModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface HttpApi {

    @GET("jobs")
    fun getTicketDetailsAsync(): Deferred<List<TicketModel>>

}