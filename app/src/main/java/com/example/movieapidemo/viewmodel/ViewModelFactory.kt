package com.example.movieapidemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapidemo.Repository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(repository) as T
    }
}