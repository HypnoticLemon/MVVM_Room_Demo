package com.vikrant.simplemvvmroomdemo.Common

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.vikrant.simplemvvmroomdemo.Utils.Event
import com.vikrant.simplemvvmroomdemo.Utils.NavigationCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import java.text.SimpleDateFormat
import java.util.*


/**
 * BaseViewModel
 */
open class BaseViewModel(private val appContext: Application) : AndroidViewModel(appContext),
    CoroutineScope {

    // FOR NAVIGATION
    private val navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigationData: LiveData<Event<NavigationCommand>> = navigation

    private val parentJob = Job()

    override val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    /**
     * Convenient method to handle navigation from a [ViewModel]
     */
    fun navigateTo(directions: NavDirections) {
        navigation.value =
            Event(NavigationCommand.To(directions))
    }

    /**
     * Convenient method to handle navigation from a [ViewModel]
     */
    fun navigateBack() {
        navigation.value =
            Event(NavigationCommand.Back)
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
}