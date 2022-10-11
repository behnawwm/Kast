package com.example.kast.domain.repository

import arrow.core.Either
import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.model.Movie
import com.example.kast.utils.Failure
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMoviesWithStatusByType(categoryType: CategoryType): Either<Failure.NetworkFailure, List<Movie>>
    fun getMoviesWithStatusByTypeAsFlow(categoryType: CategoryType): Flow<Either<Failure.NetworkFailure, List<Movie>>>

    suspend fun selectAllMovies(): Either<Failure.DatabaseFailure.ReadFailure, List<Movie>>
    suspend fun selectMovieById(movieId: Long): Either<Failure.DatabaseFailure.FindFailure, Movie>
    suspend fun insertMovie(movie: Movie): Either<Failure.DatabaseFailure.InsertFailure, Unit>
    suspend fun deleteAllMovies()
}