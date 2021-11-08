package com.contactlist

import android.Manifest
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.contact.di.data.Contact
import com.contactdetail.ContactDetailActivity
import com.core.CoreActivity
import com.contactlist.adapter.ContactAdapter
import com.contactlist.adapter.OnItemClickListener
import com.contactlist.databinding.ActivityContactlistBinding
import com.contactlist.di.provider.UserContactComponentProvider
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class ContactListActivity : CoreActivity(), OnItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var contactAdapter: ContactAdapter

    private lateinit var viewBinding: ActivityContactlistBinding
    private lateinit var viewModel: ContactViewModel
    private var contactUpdate: MutableLiveData<Boolean> = MutableLiveData(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_contactlist)
        viewBinding.handler = this
        viewModel = ViewModelProvider(this, viewModelFactory)[ContactViewModel::class.java]
        initUI()
        addObservable()
        requestContactList()
    }

    private fun addContactUpdateObserver() {
        applicationContext.contentResolver.registerContentObserver(
            ContactsContract.Contacts.CONTENT_URI,
            true,
            ContactContentObserver()
        )
    }

    override fun setupActivityComponent() {
        (applicationContext as UserContactComponentProvider)
            .provideUserContactComponent(this)
            .inject(this)
    }

    private fun initUI() {
        with(viewBinding.recyclerView) {
            viewBinding.recyclerView.layoutManager = linearLayoutManager
            adapter = contactAdapter
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        }
    }

    private fun addObservable() {
        lifecycleScope.launchWhenCreated {
            viewModel.userContactFlow.collectLatest {
                contactAdapter.addContacts(it)
                viewBinding.progressBar.visibility = View.GONE
            }
        }
        contactUpdate.observe(this, {
            if (it) {
                contactUpdate.value = false
                viewModel.getContacts()
            }
        })
    }

    private fun requestContactList() {
        val permission = arrayListOf(Manifest.permission.READ_CONTACTS)
        Dexter.withContext(context)
            .withPermissions(permission)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.areAllPermissionsGranted()?.let { flag ->
                        if (flag) {
                            viewBinding.progressBar.visibility = View.VISIBLE
                            viewModel.getContacts()
                            addContactUpdateObserver()
                            return@let
                        }
                        if (report.isAnyPermissionPermanentlyDenied) {
                            Toast.makeText(
                                context,
                                "Please give contact permission",
                                Toast.LENGTH_LONG
                            ).show()
                            return
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }).check()

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onItemClick(obj: Contact) {
        startActivity(ContactDetailActivity.getIntent(context, obj))
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, ContactListActivity::class.java)
        }
    }

    inner class ContactContentObserver : ContentObserver(null) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            contactUpdate.postValue(true)
        }

        override fun deliverSelfNotifications(): Boolean {
            return true
        }
    }
}