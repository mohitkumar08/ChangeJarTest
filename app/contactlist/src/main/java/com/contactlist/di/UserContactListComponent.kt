package com.contactlist.di

import androidx.recyclerview.widget.LinearLayoutManager
import com.contactlist.ContactListActivity
import com.contactlist.di.module.MainActivityModule
import com.contactlist.di.module.VMModule
import com.contact.di.ContactProviderRepository
import com.contact.di.module.ContactModule
import com.core.BaseComponent
import com.contactlist.adapter.ContactAdapter
import com.core.di.scope.AppMainScope
import com.vmcore.VMFactoryModule
import dagger.Component

@AppMainScope
@Component(
    modules = [VMFactoryModule::class,
        VMModule::class,
        MainActivityModule::class,
        ContactModule::class],
    dependencies = [BaseComponent::class]
)
interface UserContactListComponent {

    @Component.Builder
    interface Builder {
        fun dependBaseComponent(baseComponent: BaseComponent): Builder
        fun mainActivityModule(mainActivityModule: MainActivityModule): Builder
        fun build(): UserContactListComponent
    }

    fun inject(mainActivity: ContactListActivity)

    fun getUserContactRepository(): ContactProviderRepository

    fun getContactAdapter(): ContactAdapter

    fun getLinearLayoutManager(): LinearLayoutManager


}