package com.example.movieapidemo.networkmodels

import com.google.gson.annotations.SerializedName

data class NetworkMovie(

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String
)