package com.example.catstagram.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity
data class Cat(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    @ColumnInfo(name = "birth_date") val birthDate: LocalDate,
    val gender: Char,
    @ColumnInfo(name = "avatar_uri")val avatarUri: String? = null,
)
