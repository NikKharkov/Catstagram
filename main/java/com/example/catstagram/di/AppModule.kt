package com.example.catstagram.di

import androidx.room.Room
import com.example.catstagram.data.local.CatDao
import com.example.catstagram.data.local.CatDatabase
import com.example.catstagram.data.remote.YouTubeApiService
import com.example.catstagram.data.repositories.CatRepository
import com.example.catstagram.data.repositories.CatRepositoryImpl
import com.example.catstagram.data.repositories.YouTubeRepository
import com.example.catstagram.viewmodel.CatViewModel
import com.example.catstagram.viewmodel.YouTubeViewModel
import com.example.catstagram.workmanager.VideoManagerFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        VideoManagerFactory(get())
    }

    single<CatDatabase> {
        Room.databaseBuilder(get(), CatDatabase::class.java, "cat_database.db")
            .build()
    }

    single<YouTubeApiService> {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.googleapis.com/youtube/v3/")
            .build()
            .create(YouTubeApiService::class.java)
    }

    single<YouTubeRepository> {
        YouTubeRepository(get())
    }

    single<CatDao> {
        get<CatDatabase>().catDao()
    }

    single<CatRepository> {
        CatRepositoryImpl(get())
    }

    viewModel<CatViewModel> {
        CatViewModel(get())
    }

    viewModel<YouTubeViewModel> {
        YouTubeViewModel(get())
    }
}