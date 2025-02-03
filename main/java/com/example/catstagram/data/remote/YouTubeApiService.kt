package com.example.catstagram.data.remote

import com.example.catstagram.model.YouTubeResponse
import retrofit2.http.GET
import retrofit2.http.Query


val randomQueries = listOf(
    "cats", "funny cats", "cat memes", "kitten playing", "angry cats",
    "cute cats", "funny kittens", "cat jokes", "kitten videos", "grumpy cats",
    "adorable cats", "hilarious cats", "cat pictures", "kitten cuddling", "furious cats",
    "cat videos", "funny cat videos", "cat humor", "kitten sleeping", "annoyed cats",
    "cat gifs", "funny cat gifs", "cat comedy", "kitten eating", "irritated cats",
    "cat photos", "funny cat pictures", "cat antics", "kitten exploring", "upset cats",
    "cat compilation", "funny cat compilation", "cat fails", "kitten playing with toys", "mad cats",
    "cat moments", "funny cat moments", "cat adventures", "kitten climbing", "frustrated cats",
    "cat life", "funny cat life", "cat behavior", "kitten running", "displeased cats"
)

val query = randomQueries.random()

interface YouTubeApiService {

    @GET("search")
    suspend fun getVideos(
        @Query("part") part: String = "snippet",
        @Query("q") query: String = com.example.catstagram.data.remote.query,
        @Query("type") type: String = "shorts",
        @Query("order") order: String = "date",
        @Query("maxResults") maxVideos: Int = 1, //Change if you want to show more videos at page
        @Query("key") apiKey: String
    ): YouTubeResponse
}
