package com.vikrant.simplemvvmroomdemo.viewModel

import android.app.Application
import android.util.Log
import com.vikrant.simplemvvmroomdemo.Common.BaseViewModel

class MainViewModel(val app: Application) : BaseViewModel(app) {
    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    init {
        Log.d(TAG, "MainViewModel initialization")
    }
}