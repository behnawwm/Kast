package com.example.kast

import com.example.kast.data.repository.FakeRepository
import com.example.kast.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope

expect class MovieViewModel actual constructor(
    movieRepository: MovieRepository,
    fakeRepository: FakeRepository
) {
    val viewModelScope: CoroutineScope
    fun getMovies()
    fun getMovieCategories()
}