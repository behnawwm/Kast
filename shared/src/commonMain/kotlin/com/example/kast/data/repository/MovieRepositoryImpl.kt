package com.example.kast.data.repository

import arrow.core.Either
import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.model.MovieView
import com.example.kast.data.source.local.movie.MoviesDatabase
import com.example.kast.data.source.remote.movie.MovieService
import com.example.kast.domain.repository.MovieRepository
import com.example.kast.domain.mapper.toMovieEntity
import com.example.kast.domain.mapper.toMovieView
import com.example.kast.utils.Failure
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl(
    private val apiServices: MovieService,
    private val database: MoviesDatabase,
) : MovieRepository {

    override suspend fun getMoviesByType(categoryType: CategoryType): Either<Failure.NetworkFailure, List<MovieView>> {
        val result = apiServices.getMoviesByType(categoryType.url)
        return result.map { it.results.orEmpty().map { it.toMovieView() } }
    }

    @OptIn(FlowPreview::class)
    override suspend fun selectAllMovies(): Flow<Either<Failure.DatabaseFailure.ReadFailure, List<MovieView>>> {
        return database.selectAllMovies().flatMapConcat {
            flow {
                emit(
                    if (it.isEmpty())
                        Either.Left(Failure.DatabaseFailure.ReadFailure.EmptyList)
                    else
                        Either.Right(
                            it.map { it.toMovieView() }
                        )
                )
            }
        }
    }

    override suspend fun selectMovieById(movieId: Long): Either<Failure.DatabaseFailure.FindFailure, MovieView> {
        val result = database.getMovieById(movieId)?.toMovieView()
        return Either.conditionally(
            result != null,
            ifFalse = { Failure.DatabaseFailure.FindFailure.ItemNotFoundInDb },
            ifTrue = { result!! },
        )
    }


    override suspend fun insertMovie(movie: MovieView) {
        database.insertMovie(movie.toMovieEntity())
    }

    override suspend fun deleteAllMovies() {
        database.deleteAllMovies()
    }
}



