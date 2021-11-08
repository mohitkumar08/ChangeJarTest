package com.core

import android.content.Context
import android.os.Handler
import com.core.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface BaseComponent {

    @Component.Builder
    interface Builder {
        fun appModule(appModule: AppModule): Builder
        fun build(): BaseComponent
    }

    fun requireContext():Context
    fun requireHandler() :Handler

}
