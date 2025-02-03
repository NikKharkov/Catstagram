package com.example.catstagram.model

data class YouTubeResponse(
    val items: List<VideoItem>
)

data class VideoItem(
    val id: VideoId,
    val snippet: VideoSnippet
)

data class VideoId(
    val videoId: String
)

data class VideoSnippet(
    val title: String,
    val thumbnails: VideoThumbnails
)

data class VideoThumbnails(
    val medium: VideoThumbnail
)

data class VideoThumbnail(
    val url: String
)
