package com.ganesh.tickets.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ganesh.tickets.model.TicketModel

@Dao
interface TicketDao {

    @Query("select * from TicketModel")
    fun fetchTicketDetails(): List<TicketModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ticketModel: List<TicketModel>)


}