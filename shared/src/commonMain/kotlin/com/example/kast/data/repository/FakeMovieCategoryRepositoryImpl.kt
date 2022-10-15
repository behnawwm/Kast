package com.example.kast.data.repository

import arrow.core.Either
import com.example.kast.data.source.remote.model.TmdbCategory
import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.model.CategoryView
import com.example.kast.domain.repository.MovieCategoryRepository
import com.example.kast.utils.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeMovieCategoryRepositoryImpl : MovieCategoryRepository {

    private val categoryList = listOf(
        TmdbCategory(0, "Popular", "Movies"),
        TmdbCategory(1, "Top Rated", "Movies"),
        TmdbCategory(2, "Upcoming", "Movies"),
        TmdbCategory(3, "Now Playing", "Movies"),
    )


    override suspend fun getMovieCategories(): Either<Failure.NetworkFailure, List<TmdbCategory>> {
        delay(500)
        return Either.Right(categoryList)
    }

}