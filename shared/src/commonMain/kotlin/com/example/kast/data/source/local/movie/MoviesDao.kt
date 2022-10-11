package com.example.kast.data.source.local.movie

import com.example.kast.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MoviesDao {
    suspend fun selectAllMovies(): List<MovieEntity>
     fun selectAllMoviesFlow(): Flow<List<MovieEntity>>
    suspend fun insertMovie(movie: MovieEntity)
    suspend fun getMovieById(movieId: Long): MovieEntity?
    suspend fun deleteAllMovies()
}