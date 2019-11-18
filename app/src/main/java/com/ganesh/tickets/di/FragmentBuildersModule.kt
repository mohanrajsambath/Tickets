package com.ganesh.tickets.di


import com.ganesh.tickets.view.TicketDetailsFragment
import com.ganesh.tickets.view.TicketListFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): TicketListFragment

    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): TicketDetailsFragment


}
