package com.example.kast.presentation

import com.example.kast.domain.model.CategoryType
import com.example.kast.data.repository.MovieRepositoryImpl
import kotlinx.coroutines.CoroutineScope

actual class MovieViewModel actual constructor(movieRepository: MovieRepositoryImpl) {
    actual val viewModelScope: CoroutineScope
        get() = TODO("Not yet implemented")

    actual fun getMovies(type: CategoryType) {
    }
}