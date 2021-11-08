package com.contact.di

import com.contact.di.data.Contact
import kotlinx.coroutines.flow.Flow

interface ContactProviderRepository {
    fun getContact(): Flow<List<Contact>>
}