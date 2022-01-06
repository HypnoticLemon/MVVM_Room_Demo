package com.vikrant.simplemvvmroomdemo.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FestivalDao {
    @Query("SELECT * From festival_table")
    suspend fun getFestivalList(): List<Festival>

    @Query("SELECT * From festival_table WHERE EventMonth=(:month) AND EventYear=(:year) ORDER BY EventDate")
    suspend fun getFestivalListFromMonth(month: Int, year: Int): List<Festival>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewFestival(festivalEntity: List<Festival>)

    @Query("DELETE FROM festival_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM festival_table ORDER BY Id DESC LIMIT 1")
    suspend fun getLastRowFromTable(): Festival?
}