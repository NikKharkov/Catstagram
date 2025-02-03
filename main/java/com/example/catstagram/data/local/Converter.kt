package com.example.catstagram.data.local


import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

class Converter {

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date.toString()
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it) }
    }
}
