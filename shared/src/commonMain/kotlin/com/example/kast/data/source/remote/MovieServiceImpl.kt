package com.example.kast.data.source.remote

import com.example.kast.data.model.TmdbMovieResult
import io.ktor.client.request.*

class MovieServiceImpl(
    private val apiClient: ApiClient,
) : MovieService {

    override suspend fun getPopularMovies(): TmdbMovieResult? {
        return apiClient.getResponse("3/movie/popular") {}
    }

    override suspend fun getMovies(url: String, page: Int): TmdbMovieResult? {
        return apiClient.getResponse("3/movie$url") {
            parameter("page", page)
        }
    }

}