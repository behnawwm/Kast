package com.example.kast.domain.usecase

import arrow.core.Either
import com.example.kast.data.source.remote.model.TmdbCategory
import com.example.kast.domain.mapper.toCategory
import com.example.kast.domain.model.Category
import com.example.kast.domain.model.CategoryView
import com.example.kast.domain.repository.MovieCategoryRepository
import com.example.kast.utils.Failure
import com.example.kast.utils.UseCase

class GetRemoteMovieCategoriesUseCase(private val movieCategoryRepository: MovieCategoryRepository) :
    UseCase<List<TmdbCategory>, GetRemoteMovieCategoriesUseCase.Params>() {

    data class Params(val none: Unit)

    override suspend fun run(params: Params): Either<Failure.NetworkFailure, List<TmdbCategory>> {
        return movieCategoryRepository.getMovieCategories()
    }
}