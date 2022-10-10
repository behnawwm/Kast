package com.example.kast.data.repository

import arrow.core.Either
import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.model.MovieView
import com.example.kast.data.source.local.movie.MoviesDatabase
import com.example.kast.data.source.remote.model.TmdbMovie
import com.example.kast.data.source.remote.movie.MovieService
import com.example.kast.domain.mapper.toMovie
import com.example.kast.domain.repository.MovieRepository
import com.example.kast.domain.mapper.toMovieEntity
import com.example.kast.domain.model.Movie
import com.example.kast.utils.Failure
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl(
    private val apiServices: MovieService,
    private val database: MoviesDatabase,
) : MovieRepository {

    override suspend fun getMoviesByType(categoryType: CategoryType): Either<Failure.NetworkFailure, List<TmdbMovie>> {
        val result = apiServices.getMoviesByType(categoryType.url)
        return result.map { it.results.orEmpty() }
    }

    override suspend fun selectAllMovies(): Either<Failure.DatabaseFailure.ReadFailure, List<Movie>> {
        return Either.Right(database.selectAllMovies().map { it.toMovie() }) //todo handle errors
    }

    override suspend fun selectMovieById(movieId: Long): Either<Failure.DatabaseFailure.FindFailure, Movie> {
        val result = database.getMovieById(movieId)?.toMovie()
        return Either.conditionally(
            result != null,
            ifFalse = { Failure.DatabaseFailure.FindFailure.ItemNotFoundInDb },
            ifTrue = { result!! },
        )
    }

    override suspend fun insertMovie(movie: Movie): Either<Failure, Unit> {
        return Either.Right(database.insertMovie(movie.toMovieEntity())) //todo handle errors
    }


    override suspend fun deleteAllMovies() {
        database.deleteAllMovies()
    }
}



