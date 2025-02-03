package com.example.catstagram.ui.videos

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.catstagram.model.VideoItem
import io.github.ilyapavlovskii.multiplatform.youtubeplayer.SimpleYouTubePlayerOptionsBuilder
import io.github.ilyapavlovskii.multiplatform.youtubeplayer.YouTubePlayer
import io.github.ilyapavlovskii.multiplatform.youtubeplayer.YouTubePlayerHostState
import io.github.ilyapavlovskii.multiplatform.youtubeplayer.YouTubePlayerState
import io.github.ilyapavlovskii.multiplatform.youtubeplayer.YouTubeVideoId
import kotlinx.coroutines.launch

@Composable
fun VideosScreen(
    videos: List<VideoItem>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(videos) { video ->
            VideoItem(video)
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun VideoItem(
    video: VideoItem,
) {
    val hostState = remember { YouTubePlayerHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        hostState.loadVideo(YouTubeVideoId(video.id.videoId))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        YouTubePlayer(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(8.dp))
                .padding(16.dp),
            hostState = hostState,
            options = SimpleYouTubePlayerOptionsBuilder.builder {
                autoplay(true)
                controls(true)
                rel(true)
                ivLoadPolicy(true)
                ccLoadPolicy(true)
                fullscreen = true
            },
        )

        when (val state = hostState.currentState) {
            is YouTubePlayerState.Error -> {

            }

            YouTubePlayerState.Idle -> {

            }

            is YouTubePlayerState.Playing -> {

            }

            YouTubePlayerState.Ready -> coroutineScope.launch {
                hostState.loadVideo(YouTubeVideoId(video.id.videoId))
            }
        }
    }
}

