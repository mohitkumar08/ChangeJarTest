package com.contactlist.di.provider

import android.app.Activity
import com.contactlist.di.UserContactListComponent

interface UserContactComponentProvider {
    fun provideUserContactComponent(activity: Activity): UserContactListComponent
}