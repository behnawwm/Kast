package com.example.kast.domain.usecase

import arrow.core.Either
import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.model.Movie
import com.example.kast.domain.model.MovieDetails
import com.example.kast.domain.model.MovieView
import com.example.kast.domain.repository.MovieRepository
import com.example.kast.utils.Failure
import com.example.kast.utils.UseCase

class GetMovieDetailsUseCase(
    private val movieRepository: MovieRepository,
) :
    UseCase<MovieDetails, GetMovieDetailsUseCase.Params>() {

    data class Params(val movieId: Long)

    override suspend fun run(params: Params): Either<Failure.NetworkFailure, MovieDetails> {
        return movieRepository.getMovieDetails(params.movieId)
    }

}