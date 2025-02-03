package com.example.catstagram.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {

    @Insert
    suspend fun insertCat(cat: Cat)

    @Query("SELECT * from Cat")
    fun getCats() : Flow<List<Cat>>

    @Query("UPDATE cat SET avatar_uri = :fileName WHERE id = :id")
    suspend fun saveUri(fileName: String,id: Int)

}