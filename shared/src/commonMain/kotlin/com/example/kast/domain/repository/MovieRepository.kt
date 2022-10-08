package com.example.kast.domain.repository

import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.model.MovieView
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getDynamicMovies(categoryType: CategoryType): Flow<List<MovieView>?>
    fun selectAllMovies(): Flow<List<MovieView>>

    suspend fun selectMovieById(movieId: Long): MovieView?
    suspend fun insertMovie(movie: MovieView)
    suspend fun deleteAllMovies()
}