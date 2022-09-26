package com.example.kast

import com.example.kast.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope

actual class MovieViewModel actual constructor(movieRepository: MovieRepository) {
    actual val viewModelScope: CoroutineScope
        get() = TODO("Not yet implemented")

    actual fun getMovies() {
    }
}