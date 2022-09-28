package com.example.kast.data.source.remote

import com.example.kast.data.model.TmdbMovie
import com.example.kast.data.model.TmdbMovieResult

interface MovieServices {
    suspend fun getPopularMovies(): TmdbMovieResult?
}