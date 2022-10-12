package com.example.kast.domain.usecase

import arrow.core.Either
import com.example.kast.MovieEntity
import com.example.kast.domain.mapper.toMovie
import com.example.kast.domain.model.Movie
import com.example.kast.domain.model.MovieView
import com.example.kast.domain.repository.MovieRepository
import com.example.kast.utils.Failure
import com.example.kast.utils.UseCase

class InsertMovieUseCase(private val movieRepository: MovieRepository) :
    UseCase<Movie, InsertMovieUseCase.Params>() {

    data class Params(val movie: MovieView)

    override suspend fun run(params: Params): Either<Failure.DatabaseFailure.InsertFailure, Movie> {
        return movieRepository.insertMovie(params.movie.toMovie())
    }
}