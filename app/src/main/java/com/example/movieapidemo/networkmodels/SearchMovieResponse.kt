package com.example.movieapidemo.networkmodels

import com.google.gson.annotations.SerializedName

data class SearchMovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movies: List<NetworkMovie>
)