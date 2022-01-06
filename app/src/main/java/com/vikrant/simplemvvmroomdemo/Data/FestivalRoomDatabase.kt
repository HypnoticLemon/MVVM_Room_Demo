package com.vikrant.simplemvvmroomdemo.Data

import android.content.Context
import androidx.room.*

@Database(entities = [Festival::class], version = 1)
abstract class FestivalRoomDatabase : RoomDatabase() {

    abstract fun festivalDao(): FestivalDao

    companion object {
        private var INSTANSE: FestivalRoomDatabase? = null

        fun getDatabase(context: Context): FestivalRoomDatabase? {
            synchronized(FestivalRoomDatabase::class.java) {
                if (INSTANSE == null) {
                    INSTANSE = Room.databaseBuilder(
                        context.applicationContext,
                        FestivalRoomDatabase::class.java,
                        "festival_database"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANSE
        }
    }

}