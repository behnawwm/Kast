package com.example.kast.data.source.local.movie

import com.example.kast.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MoviesDao {
    fun selectAllMovies(): Flow<List<MovieEntity>>
    suspend fun insertMovie(movie: MovieEntity)
    suspend fun getMovieById(movieId: Long): MovieEntity?
    suspend fun deleteAllMovies()
}