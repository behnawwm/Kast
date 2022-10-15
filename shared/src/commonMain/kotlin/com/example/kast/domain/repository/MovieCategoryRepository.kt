package com.example.kast.domain.repository

import arrow.core.Either
import com.example.kast.data.source.remote.model.TmdbCategory
import com.example.kast.domain.model.CategoryView
import com.example.kast.utils.Failure
import kotlinx.coroutines.flow.Flow

interface MovieCategoryRepository {
    suspend fun getMovieCategories(): Either<Failure.NetworkFailure, List<TmdbCategory>>
}