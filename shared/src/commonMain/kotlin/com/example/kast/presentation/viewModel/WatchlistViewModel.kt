package com.example.kast.presentation.viewModel

import com.example.kast.data.repository.MovieRepositoryImpl
import com.example.kast.domain.repository.MovieRepository

expect class WatchlistViewModel actual constructor(
    movieRepository: MovieRepository,
) {
    fun getBookmarkedMovies()
}