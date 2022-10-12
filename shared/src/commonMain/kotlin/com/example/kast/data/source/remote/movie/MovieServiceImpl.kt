package com.example.kast.data.source.remote.movie

import arrow.core.Either
import com.example.kast.data.source.remote.model.TmdbMovie
import com.example.kast.data.source.remote.model.TmdbPagingResult
import com.example.kast.data.source.remote.ApiClient
import com.example.kast.utils.Failure
import kotlinx.coroutines.delay
import kotlin.random.Random

class MovieServiceImpl(
    private val apiClient: ApiClient,
) : MovieService {

    override suspend fun getMoviesByType(url: String): Either<Failure.NetworkFailure, TmdbPagingResult<TmdbMovie>> {
        return apiClient.getResponseWithApiKey("3/movie/$url")
    }
}