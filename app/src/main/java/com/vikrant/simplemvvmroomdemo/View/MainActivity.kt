package com.vikrant.simplemvvmroomdemo.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.vikrant.simplemvvmroomdemo.Common.viewModelFactory
import com.vikrant.simplemvvmroomdemo.R
import com.vikrant.simplemvvmroomdemo.databinding.ActivityMainBinding
import com.vikrant.simplemvvmroomdemo.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mMainBinding: ActivityMainBinding
    private lateinit var mMainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mMainBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        mMainViewModel = ViewModelProvider(
            this,
            viewModelFactory {
                MainViewModel(application)
            }).get(MainViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}