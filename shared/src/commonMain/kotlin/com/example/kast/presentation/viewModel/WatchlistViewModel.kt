package com.example.kast.presentation.viewModel

import com.example.kast.data.repository.MovieRepositoryImpl
import com.example.kast.domain.repository.MovieRepository
import com.example.kast.domain.usecase.GetLocalMoviesUseCase

expect class WatchlistViewModel actual constructor(
    getLocalMoviesUseCase: GetLocalMoviesUseCase,
) {
    fun getBookmarkedMovies()
}