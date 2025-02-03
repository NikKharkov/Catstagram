package com.example.catstagram.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Cat::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    Converter::class
)
abstract class CatDatabase : RoomDatabase() {

    abstract fun catDao(): CatDao

}