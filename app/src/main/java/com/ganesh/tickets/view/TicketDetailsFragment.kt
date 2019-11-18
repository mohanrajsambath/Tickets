package com.ganesh.tickets.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ganesh.tickets.binding.FragmentDataBindingComponent
import com.ganesh.tickets.di.Injectable
import com.ganesh.tickets.R
import com.ganesh.tickets.databinding.TicketDetailsFragmentBinding
import com.ganesh.tickets.view_model.TicketListViewModel
import javax.inject.Inject

class TicketDetailsFragment : BaseFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: TicketListViewModel

    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private lateinit var binding: TicketDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val dataBinding = DataBindingUtil.inflate<TicketDetailsFragmentBinding>(
            inflater,
            R.layout.ticket_details_fragment,
            container,
            false,
            dataBindingComponent
        )

        binding = dataBinding
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!, viewModelFactory)
            .get(TicketListViewModel::class.java)

        // get selected item details
        getTicketDetailsData()

    }

    /**
     * collect currently selected item details and assign the vales to views
     */
    private fun getTicketDetailsData() {
        binding.ticketModel = viewModel.getSelectedTicketDetails()
    }

}