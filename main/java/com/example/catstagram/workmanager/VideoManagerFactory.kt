package com.example.catstagram.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.catstagram.data.repositories.YouTubeRepository

class VideoManagerFactory(
    private val youTubeRepository: YouTubeRepository
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters,
    ): ListenableWorker? {
        return when(workerClassName){
            VideoManager::class.java.name -> VideoManager(appContext, workerParameters, youTubeRepository)
            else -> null
        }
    }
}