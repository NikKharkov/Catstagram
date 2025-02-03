package com.example.catstagram.data.repositories

import com.example.catstagram.data.local.Cat
import com.example.catstagram.data.local.CatDao
import kotlinx.coroutines.flow.Flow

class CatRepositoryImpl(
    private val catDao: CatDao
) : CatRepository {

    override suspend fun insertCat(cat: Cat) = catDao.insertCat(cat)

    override fun getCats(): Flow<List<Cat>> = catDao.getCats()

    override suspend fun saveUri(fileName: String, id: Int) = catDao.saveUri(fileName, id)
}