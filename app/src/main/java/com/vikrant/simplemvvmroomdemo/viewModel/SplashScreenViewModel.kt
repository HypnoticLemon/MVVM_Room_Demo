package com.vikrant.simplemvvmroomdemo.viewModel

import android.app.Application
import androidx.lifecycle.liveData
import com.vikrant.simplemvvmroomdemo.Common.BaseViewModel
import com.vikrant.simplemvvmroomdemo.Data.Festival
import com.vikrant.simplemvvmroomdemo.Utils.NetworkStateHelper
import com.vikrant.simplemvvmroomdemo.repository.DashboardRepository
import kotlinx.coroutines.Dispatchers

class SplashScreenViewModel(var app:Application) : BaseViewModel(app) {

    private var dashboardRepository: DashboardRepository = DashboardRepository(app)

    suspend fun getLastRowFromTable(): Festival? = dashboardRepository.getLastRowFromTable()

    suspend fun saveAllYadiListIntoDB(list: List<Festival>) = dashboardRepository.saveFestivalList(list)


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


}