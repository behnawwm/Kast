package com.example.kast.presentation.viewModel

import com.example.kast.data.repository.MovieRepositoryImpl
import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.usecase.GetLocalMoviesUseCase
import kotlinx.coroutines.CoroutineScope

actual class WatchlistViewModel actual constructor(
    private val getLocalMoviesUseCase: GetLocalMoviesUseCase,
) {
    actual fun getBookmarkedMovies() {
    }

}