package com.example.kast.data.repository

import com.example.kast.domain.model.Category
import com.example.kast.data.source.remote.movie_category.MovieCategoryService
import com.example.kast.domain.repository.MovieCategoryRepository
import kotlinx.coroutines.flow.Flow

class MovieCategoryRepositoryImpl(
    private val apiService: MovieCategoryService,
) : MovieCategoryRepository {

    override fun getMovieCategories(): Flow<List<Category>> {
        TODO()
    }

}