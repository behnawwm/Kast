package com.example.kast.data.repository

import com.example.kast.MovieEntity
import com.example.kast.data.model.MovieView
import com.example.kast.data.model.TmdbMovie
import com.example.kast.data.source.local.MoviesDatabase
import com.example.kast.data.source.remote.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(
    private val apiServices: MovieService,
    private val database: MoviesDatabase
) {

    fun getPopularMovies(): Flow<List<TmdbMovie>?> {
        return flow {
            emit(apiServices.getPopularMovies()?.results)
        }.flowOn(Dispatchers.Default)
    }

    @OptIn(FlowPreview::class)
    fun selectAllMovies(): Flow<List<MovieView>> {
        return database.selectAllMovies().flatMapConcat {
            flow {
                it.map {
                    it.toMovieView()
                }
            }
        }
    }

    suspend fun selectMovieById(movieId: Long): MovieView? {
        return database.getMovieById(movieId)?.toMovieView()
    }

    suspend fun insertMovie(movie: MovieView) {
        database.insertMovie(movie.toMovieEntity())
    }

    suspend fun deleteAllMovies() {
        database.deleteAllMovies()
    }
}

fun MovieEntity.toMovieView(): MovieView {
    return MovieView(id, title, rating, posterPath)
}

fun MovieView.toMovieEntity(): MovieEntity {
    return MovieEntity(id, title, rating, posterPath)
}