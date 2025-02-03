package com.example.catstagram.data.repositories

import com.example.catstagram.data.local.Cat
import kotlinx.coroutines.flow.Flow

interface CatRepository {

    suspend fun insertCat(cat: Cat)

    fun getCats(): Flow<List<Cat>>

    suspend fun saveUri(fileName: String, id: Int)

}