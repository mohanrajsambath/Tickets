package com.ganesh.tickets.view_model


import com.ganesh.tickets.BaseTest

import com.ganesh.tickets.data.repository.RepositoryImpl
import com.ganesh.tickets.model.Resource
import com.ganesh.tickets.model.TicketModel

import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TicketListViewModelTest : BaseTest() {
    @Mock
    lateinit var repository: RepositoryImpl

    @InjectMocks
    lateinit var viewModel: TicketListViewModel

    private lateinit var spyViewModel: TicketListViewModel


    @Before
    fun initAll() {
        MockitoAnnotations.initMocks(this)
        viewModel = TicketListViewModel(repository)
        spyViewModel = Mockito.spy(viewModel)
    }

    @Test
    fun getTicketDetails_validInput_success() {

        val responseData = listOf(createData())

        val result = Resource.Success(responseData)

        runBlocking {

            Mockito.`when`(repository.getTicketDetails()).thenReturn(
                result
            )
        }

        spyViewModel.getTicketListLiveData().observeForever {

        }

        spyViewModel.getTicketDetails()

        Mockito.verify(spyViewModel).parseResponse(result)

    }


    private fun createData(): TicketModel {
        return TicketModel(
            "",
            "",
            "",
            "",
            "",
            "",
            "123",
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
