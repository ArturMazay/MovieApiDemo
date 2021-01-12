package com.example.movieapidemo.viewmodel

import com.example.movieapidemo.models.Movie

sealed class MoviesResult
class ValidResult(val result: List<Movie>) : MoviesResult()
object EmptyResult : MoviesResult()
object EmptyQuery : MoviesResult()
class ErrorResult(val e: Throwable) : MoviesResult()
object TerminalError : MoviesResult()