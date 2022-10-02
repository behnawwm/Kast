package com.example.kast

import com.example.kast.data.model.CategoryType
import com.example.kast.data.repository.FakeRepository
import com.example.kast.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope

actual class AddToListsViewModel actual constructor(
    movieRepository: MovieRepository,
) {
    actual val viewModelScope: CoroutineScope
        get() = TODO("Not yet implemented")

    actual fun getMovie(movieId: Long) {
    }
}