package com.example.kast.data.source.remote

import com.example.kast.data.model.TmdbMovie

class MovieServicesImpl(
    private val apiClient: ApiClient
) : MovieServices {
    override suspend fun getPopularMovies(): List<TmdbMovie>? {
        return apiClient.getResponse("3/movie/popular")
    }


}