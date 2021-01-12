package com.example.movieapidemo

import android.util.Log
import com.example.movieapidemo.models.Movie
import com.example.movieapidemo.networkmodels.NetworkMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

//поиск фильмов! по заданной строке "query"

class Repository(private val moviesApi: MoviesApi) {

    @FlowPreview
    suspend fun searchMovies(query: String, page: Int = 1): List<Movie> {
        return withContext(Dispatchers.IO) {
            flowOf(                                             ////Создает поток, который производит значения из указанных аргументов
                moviesApi.searchMovie(API_KEY, query, page) )
        }
            .flowOn(Dispatchers.IO)                   //Изменяет контекст, в котором этот поток выполняется, на данный контекст . Этот оператор является составным и влияет только на предыдущие операторы, не имеющие собственного контекста
            .onEach { Log.d(Repository::class.java.name, it.movies.toString()) }   //Выполняет заданное действие
            .flatMapMerge { it.movies.asFlow() }        //Преобразует элементы, излучаемые исходным потоком, применяя преобразование , которое возвращает другой поток, а затем объединяет и сглаживает эти потоки.
            .map { Movie(it.id, it.title, getPosterUrl(it)) }
            .toList()
    }

    private fun getPosterUrl(it: NetworkMovie) =  "${BASE_IMAGE_URL}${it.posterPath}"

    companion object {
        const val API_KEY = "f9a69c2690fc50b90e76792d9601d4fb"
        private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"

    }
}