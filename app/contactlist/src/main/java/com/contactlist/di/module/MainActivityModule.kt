package com.contactlist.di.module

import android.app.Activity
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.contactlist.adapter.ContactAdapter
import com.contactlist.adapter.OnItemClickListener
import com.core.di.scope.AppMainScope
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(private val activity: Activity) {

    @AppMainScope
    @Provides
    fun provideLinearLayoutManager(context: Context): LinearLayoutManager {
        return LinearLayoutManager(context)
    }

    @AppMainScope
    @Provides
    fun provideContactAdapter(context: Context): ContactAdapter =
        ContactAdapter(context, activity as OnItemClickListener)
}