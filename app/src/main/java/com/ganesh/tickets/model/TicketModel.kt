package com.ganesh.tickets.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["order_id"])
data class TicketModel(
    @field:SerializedName("__status")
    val __status: String?,
    @field:SerializedName("customer_name")
    val customer_name: String?,
    @field:SerializedName("distance")
    val distance: String?,
    @field:SerializedName("job_datorder_duratione")
    val job_date: String?,
    @field:SerializedName("extras")
    val extras: String?,
    @field:SerializedName("order_duration")
    val order_duration: String?,
    @field:SerializedName("order_id")
    val order_id: String,
    @field:SerializedName("order_time")
    val order_time: String?,
    @field:SerializedName("payment_method")
    val payment_method: String?,
    @field:SerializedName("price")
    val price: String?,
    @field:SerializedName("recurrency")
    val recurrency: String?,
    @field:SerializedName("job_city")
    val job_city: String?,
    @field:SerializedName("job_latitude")
    val job_latitude: String?,
    @field:SerializedName("job_longitude")
    val job_longitude: String?,
    @field:SerializedName("job_postalcode")
    val job_postalcode: String?,
    @field:SerializedName("job_street")
    val job_street: String?,
    @field:SerializedName("status")
    val status: String?

)