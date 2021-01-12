package com.example.movieapidemo

import com.example.movieapidemo.networkmodels.SearchMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): SearchMovieResponse

}