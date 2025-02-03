package com.example.catstagram.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.catstagram.data.repositories.YouTubeRepository

class VideoManager(
    context: Context,
    params: WorkerParameters,
    private val youTubeRepository: YouTubeRepository,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        youTubeRepository.getCatVideos()
        return Result.success()
    }
}