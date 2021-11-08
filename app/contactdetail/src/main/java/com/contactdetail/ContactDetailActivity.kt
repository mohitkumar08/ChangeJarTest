package com.contactdetail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.contact.di.data.Contact
import com.contactdetail.databinding.ActivityContactDetailBinding
import com.core.CoreActivity
import com.utils.*

class ContactDetailActivity : CoreActivity() {
    private lateinit var viewBinding: ActivityContactDetailBinding
    private var contact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_contact_detail)
        viewBinding.handler = this
        supportActionBar?.hide()

        contact = intent.getParcelableExtra<Contact>(ARG_CONTACT)?.let {
            with(viewBinding) {
                initUI(it.contactColor)
                name.text = it.name
                phone.text = it.rawPhone
                icPhone.setVectorTint(it.contactColor)
                icMessage.setVectorTint(it.contactColor)
                imageView.setBackgroundColor(it.contactColor)
                it.photoUri?.let { image ->
                    image.loadImage(applicationContext, imageView)
                    name.setTextColor(Color.BLACK)
                }
                it.emails?.getOrNull(0)?.let { emailId ->
                    ivEmail.setVectorTint(it.contactColor)
                    email.text = emailId
                    cvEmail.visibility = View.VISIBLE
                }
            }
            it
        }
    }

    private fun initUI(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }
    }

    override fun setupActivityComponent() {
    }

    fun call() {
        contact?.phoneNumber?.let {
            applicationContext.call(it)
        }
    }

    fun sendMessage() {
        contact?.phoneNumber?.let {
            applicationContext.sendSMS(it)
        }
    }

    fun email() {
        contact?.emails?.getOrNull(0)?.let {
            applicationContext.sendEmail(it)
        }
    }

    companion object {
        private const val ARG_CONTACT = "contact_obj"
        fun getIntent(context: Context, data: Contact): Intent {
            return Intent(context, ContactDetailActivity::class.java).apply {
                putExtra(ARG_CONTACT, data)
            }
        }
    }
}