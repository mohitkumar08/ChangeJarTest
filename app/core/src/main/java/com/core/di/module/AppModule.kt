package com.core.di.module

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Application) {

    @Singleton
    @Provides
    fun provideContext(): Context = context.applicationContext


    @Provides
    @Singleton
    fun provideMainHandler(): Handler = Handler(Looper.getMainLooper())


}