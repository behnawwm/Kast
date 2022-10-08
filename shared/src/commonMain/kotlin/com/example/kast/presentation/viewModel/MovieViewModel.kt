package com.example.kast.presentation.viewModel

import com.example.kast.data.repository.FakeMovieCategoryRepositoryImpl
import com.example.kast.data.repository.MovieRepositoryImpl
import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.repository.MovieCategoryRepository
import com.example.kast.domain.repository.MovieRepository

expect class MovieViewModel actual constructor(
    movieRepository: MovieRepository,
    fakeRepository: MovieCategoryRepository
) {
    fun getMovies(type: CategoryType)
    fun getMovieCategories()
}