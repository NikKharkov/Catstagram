package com.example.catstagram.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catstagram.data.repositories.YouTubeRepository
import com.example.catstagram.model.VideoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class YouTubeViewModel(
    private val youTubeRepository: YouTubeRepository
) : ViewModel() {

    private val _videos = MutableStateFlow<List<VideoItem>>(emptyList())
    val videos: StateFlow<List<VideoItem>> = _videos.asStateFlow()

    init {
        viewModelScope.launch {
            fetchVideos()
        }
    }

    fun fetchVideos() {
        viewModelScope.launch {
            _videos.value = youTubeRepository.getCatVideos()
        }
    }
}