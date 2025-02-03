package com.example.catstagram.data.repositories

import com.example.catstagram.BuildConfig
import com.example.catstagram.data.remote.YouTubeApiService
import com.example.catstagram.model.VideoItem

class YouTubeRepository(
    private val youTubeApiService: YouTubeApiService
) {


    suspend fun getCatVideos(): List<VideoItem> {
        return try {
            val response = youTubeApiService.getVideos(apiKey = BuildConfig.YOUTUBE_API_KEY)
            response.items
        } catch (e: Exception) {
            emptyList()
        }
    }
}