package com.changejartest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.contactlist.ContactListActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(ContactListActivity.getIntent(this))
        finish()
    }
}