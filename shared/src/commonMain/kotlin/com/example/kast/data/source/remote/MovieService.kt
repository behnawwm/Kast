package com.example.kast.data.source.remote

import com.example.kast.data.model.CategoryTypeUrl
import com.example.kast.data.model.TmdbMovieResult

interface MovieService {
    suspend fun getPopularMovies(): TmdbMovieResult?
    suspend fun getMovies(url: String): TmdbMovieResult?
}