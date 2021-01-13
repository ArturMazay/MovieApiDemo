package com.example.movieapidemo.ui

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieapidemo.R
import com.example.movieapidemo.models.Movie

class MovieViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    private val nameMovie:TextView = itemView.findViewById(R.id.movieName)
    private val moviePoster:ImageView = itemView.findViewById(R.id.moviePoster)
    //private val editText:EditText = itemView.findViewById(R.id.tv_movie_search)

    fun bind(movie: Movie) {
        moviePoster.load(movie.poster)
        nameMovie.text=movie.name

    }

}
