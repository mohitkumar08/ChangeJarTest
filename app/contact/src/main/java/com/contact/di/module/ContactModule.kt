package com.contact.di.module

import android.content.Context
import com.contact.di.ContactProviderRepository
import com.contact.di.ContactProviderRepositoryImpl
import com.core.di.scope.AppMainScope
import dagger.Module
import dagger.Provides

@Module
class ContactModule {
    @AppMainScope
    @Provides
     fun provideContactProviderRepository(context: Context): ContactProviderRepository {
         return  ContactProviderRepositoryImpl(context)
     }
}