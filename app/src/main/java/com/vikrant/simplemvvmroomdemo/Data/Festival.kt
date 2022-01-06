package com.vikrant.simplemvvmroomdemo.Data

import androidx.room.*
import com.vikrant.simplemvvmroomdemo.Data.Festival.Companion.DESCRIPTION
import com.vikrant.simplemvvmroomdemo.Data.Festival.Companion.TABLE_NAME
import com.vikrant.simplemvvmroomdemo.Data.Festival.Companion.TITHI_DATE
import com.vikrant.simplemvvmroomdemo.Data.Festival.Companion.TITHI_DAY
import com.vikrant.simplemvvmroomdemo.Data.Festival.Companion.TITHI_DD
import com.vikrant.simplemvvmroomdemo.Data.Festival.Companion.TITHI_MM
import com.vikrant.simplemvvmroomdemo.Data.Festival.Companion.TITHI_YYYY


@Entity(
    tableName = TABLE_NAME,
    indices = [Index(TITHI_DATE), Index(DESCRIPTION),
        Index(TITHI_DAY), Index(TITHI_MM), Index(TITHI_DD), Index(TITHI_YYYY)]
)
data class Festival(
    @PrimaryKey
    @ColumnInfo(name = FESTIVAL_ID) var id: Int,
    @ColumnInfo(name = TITHI_DATE) var tithiDate: String,
    @ColumnInfo(name = DESCRIPTION) var description: String,
    @ColumnInfo(name = TITHI_DAY) var tithiDay: String,
    @ColumnInfo(name = TITHI_DD) var tithiDD: Int,
    @ColumnInfo(name = TITHI_MM)
    var tithiMM: Int,
    @ColumnInfo(name = TITHI_YYYY) var tithiYYYY: Int,
) {

    companion object {
        const val TABLE_NAME = "festival_table"
        const val FESTIVAL_ID = "Id"
        const val TITHI_DATE = "Date"
        const val DESCRIPTION = "Content"
        const val TITHI_DAY = "Day"
        const val TITHI_MM = "EventMonth"
        const val TITHI_DD = "EventDate"
        const val TITHI_YYYY = "EventYear"
    }
}
