package com.changejartest

import android.app.Activity
import android.app.Application
import com.contactlist.di.DaggerUserContactListComponent
import com.contactlist.di.UserContactListComponent
import com.contactlist.di.module.MainActivityModule
import com.contactlist.di.provider.UserContactComponentProvider
import com.core.BaseComponent
import com.core.DaggerBaseComponent
import com.core.di.CoreComponentProvider
import com.core.di.module.AppModule

class ContactApplication : Application(), CoreComponentProvider, UserContactComponentProvider {

    private val baseComponent by lazy {
        DaggerBaseComponent.builder().appModule(AppModule(this)).build()
    }

    override fun provideBaseComponent(): BaseComponent {
        return baseComponent
    }


    override fun provideUserContactComponent(activity: Activity): UserContactListComponent {
        return DaggerUserContactListComponent.builder()
            .mainActivityModule(MainActivityModule(activity))
            .dependBaseComponent(provideBaseComponent())
            .build()
    }

}
