package com.example.kast.domain.usecase

import arrow.core.Either
import com.example.kast.data.source.remote.model.TmdbMovie
import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.model.MovieView
import com.example.kast.domain.repository.MovieRepository
import com.example.kast.utils.Failure
import com.example.kast.utils.UseCase

class GetRemoteMoviesByTypeUseCase(private val movieRepository: MovieRepository) :
    UseCase<List<TmdbMovie>, GetRemoteMoviesByTypeUseCase.Params>() {

    data class Params(val categoryType: CategoryType)

    override suspend fun run(params: Params): Either<Failure, List<TmdbMovie>> {
        return movieRepository.getMoviesByType(params.categoryType)
    }

}