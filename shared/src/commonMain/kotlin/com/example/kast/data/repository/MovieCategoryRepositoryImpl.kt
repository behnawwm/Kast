package com.example.kast.data.repository

import arrow.core.Either
import com.example.kast.data.source.remote.model.TmdbCategory
import com.example.kast.domain.model.CategoryView
import com.example.kast.data.source.remote.movie_category.MovieCategoryService
import com.example.kast.domain.repository.MovieCategoryRepository
import com.example.kast.utils.Failure
import kotlinx.coroutines.flow.Flow

class MovieCategoryRepositoryImpl(
    private val apiService: MovieCategoryService,
) : MovieCategoryRepository {

    override suspend fun getMovieCategories(): Either<Failure.NetworkFailure, List<TmdbCategory>> {
        TODO("Not yet implemented!!")
    }

}