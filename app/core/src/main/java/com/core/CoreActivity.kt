package com.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class CoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setupActivityComponent()
        super.onCreate(savedInstanceState)
    }
    protected abstract fun setupActivityComponent()

}