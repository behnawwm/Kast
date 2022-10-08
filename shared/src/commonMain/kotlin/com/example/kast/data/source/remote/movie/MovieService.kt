package com.example.kast.data.source.remote.movie

import com.example.kast.data.model.TmdbMovie
import com.example.kast.data.model.TmdbPagingResult

interface MovieService {
    suspend fun getMovies(url: String): TmdbPagingResult<TmdbMovie>?
}