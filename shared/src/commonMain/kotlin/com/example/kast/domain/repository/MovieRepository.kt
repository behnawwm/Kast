package com.example.kast.domain.repository

import arrow.core.Either
import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.model.MovieView
import com.example.kast.utils.Failure
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMoviesByType(categoryType: CategoryType): Either<Failure.NetworkFailure, List<MovieView>>

    suspend fun selectAllMovies(): Flow<Either<Failure.DatabaseFailure.ReadFailure, List<MovieView>>>
    suspend fun selectMovieById(movieId: Long): Either<Failure.DatabaseFailure.FindFailure, MovieView>
    suspend fun insertMovie(movie: MovieView)
    suspend fun deleteAllMovies()
}