package com.contact.di.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Contact(
    val id: Long, val contactColor: Int,
    var name: String? = null,
    var phoneNumber: String? = null,
    var photoUri: String? = null,
    var rawPhone: String? = null,
    var emails: List<String>? = null
) : Parcelable