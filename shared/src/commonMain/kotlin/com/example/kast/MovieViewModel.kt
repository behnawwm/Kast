package com.example.kast

import com.example.kast.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope

expect class MovieViewModel actual constructor(movieRepository: MovieRepository) {
    val viewModelScope:CoroutineScope
    fun getMovies()
}