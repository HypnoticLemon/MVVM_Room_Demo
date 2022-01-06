package com.vikrant.simplemvvmroomdemo.viewModel

import android.app.Application
import com.vikrant.simplemvvmroomdemo.Common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.liveData
import com.vikrant.simplemvvmroomdemo.Data.Festival
import com.vikrant.simplemvvmroomdemo.Utils.NetworkStateHelper
import com.vikrant.simplemvvmroomdemo.repository.DashboardRepository

class DashboardViewModel(var app: Application) : BaseViewModel(app) {

    companion object {
        private val TAG = DashboardViewModel::class.java.simpleName
    }

    private var dashboardRepository: DashboardRepository = DashboardRepository(app)

    fun getFestivalList() = liveData(Dispatchers.IO) {
        emit(NetworkStateHelper.loading())
        try {
            emit(NetworkStateHelper.success(data = dashboardRepository.festivalList()))
        } catch (exception: Exception) {
            emit(
                NetworkStateHelper.error(
                    data = null,
                    message = exception.message ?: "Error Occurred!"
                )
            )
        }
    }

    suspend fun getFestivalListFromMonth(): List<Festival> =
        dashboardRepository.getFestivalList()

    suspend fun getLastRowFromTable(): Festival? = dashboardRepository.getLastRowFromTable()
}