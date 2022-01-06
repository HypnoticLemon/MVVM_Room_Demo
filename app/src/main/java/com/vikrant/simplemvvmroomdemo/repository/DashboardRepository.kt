package com.vikrant.simplemvvmroomdemo.repository

import android.app.Application
import com.vikrant.simplemvvmroomdemo.Data.FestivalDao
import com.vikrant.simplemvvmroomdemo.Data.FestivalRoomDatabase
import com.vikrant.simplemvvmroomdemo.Data.Festival
import com.vikrant.simplemvvmroomdemo.Network.RestApiClient
import com.vikrant.simplemvvmroomdemo.Network.RestApiInterface

class DashboardRepository(val app: Application) {
    private val restApiInterface = RestApiClient.createService(RestApiInterface::class.java)
    private lateinit var festivalDao: FestivalDao

    init {
        val db = FestivalRoomDatabase.getDatabase(app)
        db?.let {
            festivalDao = it.festivalDao()
        }
    }

    suspend fun festivalList() =
        restApiInterface.getFile("uc?id=16LIFBVyLLnv9RtcmilVgaUVlZwtaRc8z&export=download")

    suspend fun saveFestivalList(festivalList: List<Festival>) = festivalDao.insertNewFestival(festivalList)

    suspend fun getFestivalList() = festivalDao.getFestivalList()

    suspend fun getFestivalListFromMonth(month: Int, year: Int) =
        festivalDao.getFestivalListFromMonth(month, year)

    suspend fun getLastRowFromTable() = festivalDao.getLastRowFromTable()

}