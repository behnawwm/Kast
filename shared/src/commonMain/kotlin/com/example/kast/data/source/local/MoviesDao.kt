package com.example.kast.data.source.local

import com.example.kast.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MoviesDao {
    suspend fun insertMovie(movie: MovieEntity)
    fun selectAllMovies(): Flow<List<MovieEntity>>
    suspend fun getMovieById(movieId: Long): MovieEntity?
    suspend fun deleteAllMovies()
}