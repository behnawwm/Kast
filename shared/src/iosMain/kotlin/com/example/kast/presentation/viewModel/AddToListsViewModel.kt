package com.example.kast.presentation.viewModel

import com.example.kast.data.repository.MovieRepositoryImpl
import kotlinx.coroutines.CoroutineScope

actual class AddToListsViewModel actual constructor(
    movieRepository: MovieRepositoryImpl,
) {
    actual val viewModelScope: CoroutineScope
        get() = TODO("Not yet implemented")

    actual fun getMovie(movieId: Long) {
    }
}