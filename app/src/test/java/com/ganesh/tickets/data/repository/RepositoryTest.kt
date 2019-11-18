package com.ganesh.tickets.data.repository


import com.ganesh.tickets.BaseTest
import com.ganesh.tickets.data.local.TicketDao
import com.ganesh.tickets.data.remote.HttpApi
import com.ganesh.tickets.model.Resource
import com.ganesh.tickets.model.TicketModel
import com.ganesh.tickets.util.InternetConnection


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock

import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when`


class RepositoryTest : BaseTest() {


    @Mock
    lateinit var mockAPI: HttpApi

    @Mock
    lateinit var ticketDao: TicketDao

    @Mock
    lateinit var internetConnection: InternetConnection



    @InjectMocks
    lateinit var rateService: Repository

    @Before
    fun initAll() {
        MockitoAnnotations.initMocks(this)
        rateService = Repository(ticketDao, mockAPI, internetConnection)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getTicketDetails_valid_onlineData() {

        val list = listOf(createData())

        runBlocking {

            internetConnection.isAvailable()
            `when`(internetConnection.isAvailable()).thenReturn(true)

            `when`(
                mockAPI.getTicketDetailsAsync()
            ).thenReturn(async {
                list
            })

            val result = rateService.getTicketDetails()

            assertEquals(true, result is Resource.Success)

            assertEquals(false, result is Resource.Error)


        }


    }


    @ExperimentalCoroutinesApi
    @Test
    fun getTicketDetails_valid_offlineData() {

        val list = listOf(createData())

        runBlocking {

            internetConnection.isAvailable()
            `when`(internetConnection.isAvailable()).thenReturn(false)

            `when`(
                ticketDao.fetchTicketDetails()
            ).thenReturn(list)

            val result = rateService.getTicketDetails()

            assertEquals(true, result is Resource.Success)

            assertEquals(false, result is Resource.Error)

        }


    }

    @ExperimentalCoroutinesApi
    @Test
    fun getTicketDetails_error_online() {

        val ex = Throwable("error")

        runBlocking {
            internetConnection.isAvailable()
            `when`(internetConnection.isAvailable()).thenReturn(true)

            `when`(
                mockAPI.getTicketDetailsAsync()
            ).then {

                (async {
                    ex
                })
            }

            val result = rateService.getTicketDetails()

            assertEquals(false, result is Resource.Success)

            assertEquals(true, result is Resource.Error)
        }
    }


    private fun createData(): TicketModel {
        return TicketModel(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        )
    }
}


