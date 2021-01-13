package com.example.movieapidemo

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.movieapidemo.viewmodel.MovieViewModel
import com.example.movieapidemo.viewmodel.ViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppComponent(appContext: Context) {

    private val moviesRepo: Repository
    //private val navigator: Navigator

    init {
       // navigator = Navigator(appContext)

        val api = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)

        moviesRepo = Repository(api)
    }

    fun getMoviesViewModel(fragment: Fragment): MovieViewModel {
        return ViewModelProvider(fragment, ViewModelFactory(moviesRepo)).get(MovieViewModel::class.java)
    }
}