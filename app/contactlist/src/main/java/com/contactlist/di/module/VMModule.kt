package com.contactlist.di.module

import androidx.lifecycle.ViewModel
import com.contactlist.ContactViewModel
import com.vmcore.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class VMModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContactViewModel::class)
    abstract fun userSessionViewModel(viewModel: ContactViewModel): ViewModel

}
