package com.example.movieapidemo.viewmodel

sealed class StatusSearch
 object Loading : StatusSearch()
 object Ready : StatusSearch()
