package com.contact.di

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.telephony.PhoneNumberUtils
import android.util.Log
import com.contact.di.data.Contact
import com.core.di.scope.AppMainScope
import com.utils.getRandomColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import kotlin.collections.ArrayList


@AppMainScope
class ContactProviderRepositoryImpl constructor(private val context: Context) :
    ContactProviderRepository {
    private val PROJECTION = arrayOf(
        ContactsContract.Contacts._ID,
        Phone.NUMBER,
        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
        ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
        ContactsContract.Contacts.LOOKUP_KEY,
        ContactsContract.Contacts.PHOTO_URI,
        ContactsContract.Contacts.HAS_PHONE_NUMBER,
        Email.CONTACT_ID
    )

    @SuppressLint("Range")
    override fun getContact(): Flow<List<Contact>> = flow {
        var contact: Contact
        val cleanList: MutableMap<String, Contact> = LinkedHashMap()
        val cursor: Cursor? = context.contentResolver.query(
            Phone.CONTENT_URI,
            PROJECTION, null, null,
            Phone.DISPLAY_NAME + " ASC"
        )
        if (cursor == null) {
            emit(emptyList())
            return@flow
        }
        cursor.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndex(ContactsContract.Contacts._ID))
                val name = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNo =
                    it.getString(it.getColumnIndex(Phone.NUMBER))
                val photoUri =
                    it.getString(it.getColumnIndex(Phone.PHOTO_URI))
                contact = Contact(id, getRandomColor())
                contact.name = name
                contact.rawPhone = phoneNo
                contact.phoneNumber = PhoneNumberUtils.stripSeparators(phoneNo)
                contact.photoUri = photoUri
                contact.emails = getEmailIds(id.toString())
                cleanList[phoneNo] = contact
                Log.i("contact", "getAllContacts: $name $phoneNo $photoUri ${contact.emails}")
            }
        }
        emit(ArrayList(cleanList.values))
    }

    @SuppressLint("Range")
    private fun getEmailIds(id: String): ArrayList<String>? {
        return context.contentResolver.query(
            Email.CONTENT_URI,
            null,
            Email.CONTACT_ID + " = $id",
            null,
            ContactsContract.Contacts._ID + " ASC"
        )?.use {
            val list = arrayListOf<String>()
            while (it.moveToNext()) {
                val email = it.getString(it.getColumnIndex(Email.DATA))
                list.add(email)
            }
            list
        }
    }
}