package com.example.kast.data.repository

import arrow.core.Either
import com.example.kast.MovieEntity
import com.example.kast.data.source.local.movie.MoviesDao
import com.example.kast.domain.model.CategoryType
import com.example.kast.data.source.remote.model.TmdbMovie
import com.example.kast.data.source.remote.movie.MovieService
import com.example.kast.domain.mapper.toMovie
import com.example.kast.domain.repository.MovieRepository
import com.example.kast.domain.mapper.toMovieEntity
import com.example.kast.domain.model.Movie
import com.example.kast.utils.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

class MovieRepositoryImpl(
    private val apiService: MovieService,
    private val databaseDao: MoviesDao,
) : MovieRepository {

//    private val localMovies = MutableStateFlow<List<MovieEntity>?>(null)
//
//    init {
//        databaseDao.selectAllMoviesFlow().onEach {
//            localMovies.value = it
//        }.launchIn(CoroutineScope(Dispatchers.Default))
//    }

    override suspend fun getMoviesByType(categoryType: CategoryType): Either<Failure.NetworkFailure, List<Movie>> {
        val localMovies =
            databaseDao.selectAllMovies()
        val result = apiService.getMoviesByType(categoryType.url)
        return result.map {
            it.results.orEmpty().map {remoteMovie->
                remoteMovie.toMovie(
                    localMovies.find { it.id == remoteMovie.id}
                )
            }
        }
    }

    override suspend fun selectAllMovies(): Either<Failure.DatabaseFailure.ReadFailure, List<Movie>> {
        return Either.Right(databaseDao.selectAllMovies().map { it.toMovie() }) //todo handle errors
    }

    override suspend fun selectMovieById(movieId: Long): Either<Failure.DatabaseFailure.FindFailure, Movie> {
        val result = databaseDao.getMovieById(movieId)?.toMovie()
        return Either.conditionally(
            result != null,
            ifFalse = { Failure.DatabaseFailure.FindFailure.ItemNotFoundInDb },
            ifTrue = { result!! },
        )
    }

    override suspend fun insertMovie(movie: Movie): Either<Failure.DatabaseFailure.InsertFailure, Unit> {
        return Either.Right(databaseDao.insertMovie(movie.toMovieEntity())) //todo handle errors
    }


    override suspend fun deleteAllMovies() {
        databaseDao.deleteAllMovies()
    }
}



