package com.example.kast

import com.example.kast.data.model.CategoryType
import com.example.kast.data.repository.FakeRepository
import com.example.kast.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope

actual class WatchlistViewModel actual constructor(
    movieRepository: MovieRepository,
) {
    actual val viewModelScope: CoroutineScope
        get() = TODO("Not yet implemented")

    actual fun getBookmarkedMovies(type: CategoryType) {
    }


}