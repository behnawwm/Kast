package com.example.kast.data.source.remote.movie

import arrow.core.Either
import com.example.kast.data.source.remote.model.TmdbMovie
import com.example.kast.data.source.remote.model.TmdbPagingResult
import com.example.kast.data.source.remote.ApiClient
import com.example.kast.utils.Failure

class MovieServiceImpl(
    private val apiClient: ApiClient,
) : MovieService {

    override suspend fun getMoviesByType(url: String): Either<Failure.NetworkFailure, TmdbPagingResult<TmdbMovie>> {
        return apiClient.getResponse("3/movie/$url")
    }
}