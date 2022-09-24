package com.example.kast.data.source.remote

import com.example.kast.data.model.TmdbMovie
import io.ktor.client.*

class ApiServices(private val client: ApiClient) {
    suspend fun getPopularMovies(): List<TmdbMovie>? = client.getResponse("3/movie/popular")
}