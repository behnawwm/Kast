package com.example.kast.presentation

import com.example.kast.data.repository.MovieRepositoryImpl

expect class WatchlistViewModel actual constructor(
    movieRepository: MovieRepositoryImpl,
) {
    fun getBookmarkedMovies()
}