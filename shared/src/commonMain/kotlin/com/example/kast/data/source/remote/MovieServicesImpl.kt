package com.example.kast.data.source.remote

import com.example.kast.data.model.TmdbMovie
import com.example.kast.data.model.TmdbMovieResult

class MovieServicesImpl(
    private val apiClient: ApiClient
) : MovieServices {
    override suspend fun getPopularMovies(): TmdbMovieResult? {
        return apiClient.getResponse("movies/movies")
    }

    override suspend fun test(): String {
        return apiClient.getHtml()
    }


}