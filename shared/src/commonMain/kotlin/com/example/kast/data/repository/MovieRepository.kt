package com.example.kast.data.repository

import com.example.kast.data.model.TmdbMovie
import com.example.kast.data.source.remote.ApiClient
import com.example.kast.data.source.remote.ApiServices
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieRepository(
    private val apiServices: ApiServices
) {
    constructor() : this(apiServices = ApiServices(ApiClient()))

    fun getPopularMovies(
        onSuccess: (List<TmdbMovie>) -> Unit,
        onFailure: (Throwable) -> Unit
    ): Flow<List<TmdbMovie>?>{
        return flow { emit(apiServices.getPopularMovies()) }
            .flowOn(CoroutineContext)
    }
}