package com.core.di

import com.core.BaseComponent

interface CoreComponentProvider {
    fun provideBaseComponent(): BaseComponent
}