package com.example.kast.data.source.remote.movie

import arrow.core.Either
import com.example.kast.data.source.remote.model.TmdbMovie
import com.example.kast.data.source.remote.model.TmdbMovieDetails
import com.example.kast.data.source.remote.model.TmdbPagingResult
import com.example.kast.utils.Failure

interface MovieService {
    suspend fun getMoviesByType(url: String): Either<Failure.NetworkFailure, TmdbPagingResult<TmdbMovie>>
    suspend fun getMovieDetails(id: Long): Either<Failure.NetworkFailure, TmdbMovieDetails>
}