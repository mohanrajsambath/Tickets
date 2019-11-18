package com.ganesh.tickets.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ganesh.tickets.view_model.TicketListViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModelProviders
import com.ganesh.tickets.binding.FragmentDataBindingComponent
import com.ganesh.tickets.di.Injectable
import com.ganesh.tickets.R
import com.ganesh.tickets.databinding.TicketListFragmentBinding
import com.ganesh.tickets.model.TicketModel
import com.ganesh.tickets.view.adapter.OnTicketItemSelected
import com.ganesh.tickets.view.adapter.TicketListAdapter


class TicketListFragment : BaseFragment(), Injectable, OnTicketItemSelected {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var adapter: TicketListAdapter

    private lateinit var viewModel: TicketListViewModel

    private lateinit var binding: TicketListFragmentBinding

    private var dataBindingComponent = FragmentDataBindingComponent(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val dataBinding = DataBindingUtil.inflate<TicketListFragmentBinding>(
            inflater,
            R.layout.ticket_list_fragment,
            container,
            false,
            dataBindingComponent
        )

        binding = dataBinding
        initUI()
        return dataBinding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!, viewModelFactory)
            .get(TicketListViewModel::class.java)

        viewModelObserver()
        // get all the ticket list
        collectTickets()
    }


    private fun collectTickets() {
        viewModel.getTicketDetails()
    }

    private fun initUI() {
        binding.repoList.adapter = adapter
        adapter.setCallBack(this)
    }

    // add the data to adapter
    private fun assignAdapter(ticketList: List<TicketModel>) {
        adapter.setData(ticketList)
    }

    private fun viewModelObserver() {

        viewModel.getTicketListLiveData().observe(this, Observer {
            assignAdapter(it)
        })

        viewModel.errorMessage.observe(this, Observer {
            showMessage(it)
        })

        viewModel.canShowLoading.observe(this, Observer {

            if (it) {
                binding.progressCircular.visibility = View.GONE
            } else {
                binding.progressCircular.visibility = View.VISIBLE
            }

        })

    }

    /**
     * on user selecting an item from the list, this methods is invoked
     */
    override fun onItemSelected(selectedItem: TicketModel) {
        viewModel.onTicketSelected(selectedItem)
    }

}