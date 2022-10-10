package com.example.kast.domain.usecase

import arrow.core.Either
import com.example.kast.domain.model.MovieView
import com.example.kast.domain.repository.MovieRepository
import com.example.kast.utils.Failure
import com.example.kast.utils.UseCase

class InsertMovieUseCase(private val movieRepository: MovieRepository) :
    UseCase<Unit, InsertMovieUseCase.Params>() {

    data class Params(val movie: MovieView)

    override suspend fun run(params: Params): Either<Failure, Unit> {
        return movieRepository.insertMovie(params.movie)
    }
}