package com.ganesh.tickets.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.ganesh.tickets.model.TicketModel

@Database(entities = [TicketModel::class], version = 1, exportSchema = false)
abstract class TicketDataBase : RoomDatabase() {

    abstract fun ticketEntities(): TicketDao

    companion object {
        const val DATABASE_NAME = "job_database.db"
    }
}