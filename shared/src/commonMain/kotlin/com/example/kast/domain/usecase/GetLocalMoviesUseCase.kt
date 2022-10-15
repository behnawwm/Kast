package com.example.kast.domain.usecase

import arrow.core.Either
import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.model.Movie
import com.example.kast.domain.model.MovieView
import com.example.kast.domain.repository.MovieRepository
import com.example.kast.utils.Failure
import com.example.kast.utils.UseCase

class GetLocalMoviesUseCase(
    private val movieRepository: MovieRepository,
) :
    UseCase<List<Movie>, GetLocalMoviesUseCase.Params>() {

    data class Params(val none: Unit)

    override suspend fun run(params: Params): Either<Failure.DatabaseFailure.ReadFailure, List<Movie>> {
        return movieRepository.selectAllMovies()
    }

}