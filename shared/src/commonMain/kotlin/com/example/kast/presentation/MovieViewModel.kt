package com.example.kast.presentation

import com.example.kast.domain.model.CategoryType
import com.example.kast.data.repository.FakeMovieCategoryRepositoryImpl
import com.example.kast.data.repository.MovieRepositoryImpl

expect class MovieViewModel actual constructor(
    movieRepository: MovieRepositoryImpl,
    fakeRepository: FakeMovieCategoryRepositoryImpl
) {
    fun getMovies(type: CategoryType)
    fun getMovieCategories()
}