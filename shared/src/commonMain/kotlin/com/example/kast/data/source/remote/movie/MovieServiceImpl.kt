package com.example.kast.data.source.remote.movie

import com.example.kast.data.model.TmdbMovie
import com.example.kast.data.model.TmdbPagingResult
import com.example.kast.data.source.remote.ApiClient

class MovieServiceImpl(
    private val apiClient: ApiClient
) : MovieService {

    override suspend fun getMovies(url: String): TmdbPagingResult<TmdbMovie>? {
        return apiClient.getResponse("3/movie/$url")
    }
}