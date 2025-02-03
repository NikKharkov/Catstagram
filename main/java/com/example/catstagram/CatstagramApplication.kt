package com.example.catstagram

import android.app.Application
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.catstagram.di.appModule
import com.example.catstagram.workmanager.VideoManager
import com.example.catstagram.workmanager.VideoManagerFactory
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class CatstagramApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CatstagramApplication)
            modules(appModule)
        }

        val workerFactory: VideoManagerFactory = get()

        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.initialize(this, config)

        setupWorkManager()
    }

    private fun setupWorkManager() {
        val workRequest = PeriodicWorkRequestBuilder<VideoManager>(15, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "VideoFetchWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
