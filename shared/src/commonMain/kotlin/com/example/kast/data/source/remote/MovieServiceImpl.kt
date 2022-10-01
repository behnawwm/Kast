package com.example.kast.data.source.remote

import com.example.kast.data.model.TmdbMovieResult

class MovieServiceImpl(
    private val apiClient: ApiClient
) : MovieService {

    override suspend fun getPopularMovies(): TmdbMovieResult? {
        return apiClient.getResponse("3/movie/popular")
    }

    override suspend fun getMovies(url: String): TmdbMovieResult? {
        return apiClient.getResponse("3/movie$url")
    }

}