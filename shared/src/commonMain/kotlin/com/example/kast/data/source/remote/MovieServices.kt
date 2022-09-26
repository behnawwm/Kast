package com.example.kast.data.source.remote

import com.example.kast.data.model.TmdbMovie

interface MovieServices {
    suspend fun getPopularMovies(): List<TmdbMovie>?
}