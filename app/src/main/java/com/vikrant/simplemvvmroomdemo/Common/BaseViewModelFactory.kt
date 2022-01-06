package com.vikrant.simplemvvmroomdemo.Common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * This function is used to send the parameter in view model constructor while creating one
 */
inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
    }
