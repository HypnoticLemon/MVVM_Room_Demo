package com.vikrant.simplemvvmroomdemo.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.vikrant.simplemvvmroomdemo.Common.viewModelFactory
import com.vikrant.simplemvvmroomdemo.BuildConfig
import com.vikrant.simplemvvmroomdemo.Common.BaseFragment
import com.vikrant.simplemvvmroomdemo.Data.Festival
import com.vikrant.simplemvvmroomdemo.R
import com.vikrant.simplemvvmroomdemo.Utils.NetworkStateHelper
import com.vikrant.simplemvvmroomdemo.Utils.Status
import com.vikrant.simplemvvmroomdemo.Utils.Status.LOADING
import com.vikrant.simplemvvmroomdemo.databinding.LayoutSplashBinding
import com.vikrant.simplemvvmroomdemo.model.EventResponseModel
import com.vikrant.simplemvvmroomdemo.viewModel.SplashScreenViewModel
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var mSplashBinding: LayoutSplashBinding
    private lateinit var mViewModel: SplashScreenViewModel

    companion object {
        private const val SPLASH_TIME_OUT = 2500
        private const val TAG = "SplashScreenActivity"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSplashBinding = DataBindingUtil.setContentView(this, R.layout.layout_splash)
        mViewModel = ViewModelProvider(
            this,
            viewModelFactory {
                SplashScreenViewModel(application)
            }
        ).get(SplashScreenViewModel::class.java)

        mSplashBinding.tvVersionName.text = "v" + BuildConfig.VERSION_NAME

        GlobalScope.launch(Dispatchers.IO) {
            loadFestivalList()
        }
    }

    @SuppressLint("ShowToast")
    private suspend fun loadFestivalList() {
        if (null == mViewModel.getLastRowFromTable()) {
            GlobalScope.launch(Dispatchers.Main) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    if (isInternetAvailable()) {
                        getFestivalList()
                    } else {
                        Toast.makeText(
                            this@SplashScreenActivity,
                            "Please check your internet connection and try again later!",
                            Toast.LENGTH_LONG
                        ).show()
                        GlobalScope.launch {
                            delay(SPLASH_TIME_OUT.toLong())
                            finish()
                        }
                    }
                } else {
                    getFestivalList()
                }
            }

        } else {
            waitForTwoMinute()
        }
    }

    private fun waitForTwoMinute() {
        GlobalScope.launch {
            delay(SPLASH_TIME_OUT.toLong())
            mainActivityIntent()
        }
    }

    private fun mainActivityIntent() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    private fun getFestivalList() {;
        mViewModel.getFestivalList().observe(this) { networkResource ->
            when (networkResource.status) {
                LOADING -> {
                    Log.e(TAG, "Loading.....")
                }
                Status.SUCCESS -> {
                    Log.d(TAG, "SUCCESS")
                    setUpUserData(networkResource)
                }
                Status.ERROR -> {
                    BaseFragment.progressBar.getDialog()?.isShowing.let {
                        BaseFragment.progressBar.getDialog()?.dismiss()
                    }
                    Log.e(TAG, "Error: " + networkResource.msg)
                }
            }
        }

    }

    private fun setUpUserData(networkResource: NetworkStateHelper<EventResponseModel>) {
        GlobalScope.launch(Dispatchers.IO) {
            networkResource.data?.Data?.let {
                val festivalList = mutableListOf<Festival>()
                it.forEach { it1 ->
                    val festival = Festival(
                        it1.Id,
                        it1.Date,
                        it1.Festival,
                        it1.Day,
                        parseIntoFormattedDate(it1.Date),
                        parseIntoFormattedMonth(it1.Date),
                        parseIntoFormattedYear(it1.Date)
                    )
                    festivalList.add(festival)
                }
                mViewModel.saveAllYadiListIntoDB(festivalList)
                waitForTwoMinute()
            }
        }
    }


    @SuppressLint("SimpleDateFormat")
    fun parseIntoFormattedDate(observationTime: String): Int {
        return try {
            val sdf =
                SimpleDateFormat("dd-MM-yyyy") //04-02-2020
            val cal = Calendar.getInstance()
            sdf.timeZone = TimeZone.getTimeZone(cal.timeZone.displayName)
            val formattedDate: Date = sdf.parse(observationTime)
            cal.time = formattedDate
            val date = cal.get(Calendar.DATE)
            date
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun parseIntoFormattedMonth(observationTime: String): Int {
        return try {
            val sdf =
                SimpleDateFormat("dd-MM-yyyy") //04-02-2020
            val cal = Calendar.getInstance()
            sdf.timeZone = TimeZone.getTimeZone(cal.timeZone.displayName)
            val formattedDate: Date = sdf.parse(observationTime)
            cal.time = formattedDate
            var month = cal.get(Calendar.MONTH)
            month
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }


    @SuppressLint("SimpleDateFormat")
    fun parseIntoFormattedYear(observationTime: String): Int {
        return try {
            val sdf =
                SimpleDateFormat("dd-MM-yyyy") //04-02-2020
            val cal = Calendar.getInstance()
            sdf.timeZone = TimeZone.getTimeZone(cal.timeZone.displayName)
            val formattedDate: Date = sdf.parse(observationTime)
            cal.time = formattedDate
            var year = cal.get(Calendar.YEAR)
            year
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_VPN")
                    return true
                }
            }
        }
        return false
    }


}