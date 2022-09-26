package com.example.kast.data.repository

import com.example.kast.data.model.TmdbMovie
import com.example.kast.data.source.remote.ApiClient
import com.example.kast.data.source.remote.MovieServices
import com.example.kast.data.source.remote.MovieServicesImpl
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(
    private val apiServices: MovieServices
) {
    constructor() : this(apiServices = MovieServicesImpl(ApiClient()))

    fun getPopularMovies(): Flow<List<TmdbMovie>?> {
        return flow {
            emit(apiServices.getPopularMovies()?.results)
        }.flowOn(Dispatchers.Default)
    }

    fun test(): Flow<String> {
        return flow {
            emit(apiServices.test())
        }.flowOn(Dispatchers.Default)
    }
}