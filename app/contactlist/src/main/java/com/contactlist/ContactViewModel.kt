package com.contactlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contact.di.ContactProviderRepository
import com.contact.di.data.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var repository: ContactProviderRepository

    private val _userContactFlow = MutableSharedFlow<List<Contact>>()
    val userContactFlow: SharedFlow<List<Contact>> = _userContactFlow

    fun getContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getContact().collectLatest {
                _userContactFlow.emit(it)
            }
        }
    }
}