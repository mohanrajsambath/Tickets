package com.ganesh.tickets

import android.os.Bundle


import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ganesh.tickets.view_model.TicketListViewModel
import javax.inject.Inject
import com.ganesh.tickets.view.BaseActivity
import com.ganesh.tickets.view.TicketDetailsFragment
import com.ganesh.tickets.view.TicketListFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector


class MainActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: TicketListViewModel

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModelObserver()

        if (savedInstanceState == null) {
            loadListFragment()
        }
    }


    private fun viewModelObserver() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(TicketListViewModel::class.java)

        viewModel.getCanSHowTicketDetails().observe(this, Observer {
            loadDetailsFragment()
        })
    }

    private fun loadListFragment() {
        addFragment(
            R.id.frm_fragment_container,
            TicketListFragment(),
            "List"
        )
    }

   private fun loadDetailsFragment() {
        this.replaceFragment(
            R.id.frm_fragment_container,
            TicketDetailsFragment(),
            "Details", "back"
        )
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

}
