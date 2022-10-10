package com.example.kast.domain.usecase.home

import arrow.core.*
import com.example.kast.domain.mapper.findCategoryTypeByString
import com.example.kast.domain.mapper.toCategory
import com.example.kast.domain.model.Category
import com.example.kast.domain.model.CategoryView
import com.example.kast.domain.model.Movie
import com.example.kast.domain.usecase.GetLocalMoviesUseCase
import com.example.kast.domain.usecase.GetRemoteMovieCategoriesUseCase
import com.example.kast.domain.usecase.GetRemoteMoviesByTypeUseCase
import com.example.kast.utils.Failure
import com.example.kast.utils.FlowUseCase
import com.example.kast.utils.UseCase

class GetMovieCategoriesUseCase(
    private val getRemoteMovieCategoriesUseCase: GetRemoteMovieCategoriesUseCase,
    private val getRemoteMoviesByTypeUseCase: GetRemoteMoviesByTypeUseCase,
    private val getLocalMoviesUseCase: GetLocalMoviesUseCase,
) :
    UseCase<List<Category>, GetMovieCategoriesUseCase.Params>() {

    data class Params(val none: Unit)

    override suspend fun run(params: Params): Either<Failure.NetworkFailure, List<Category>> {
        val localMoviesEither = getLocalMoviesUseCase.run(GetLocalMoviesUseCase.Params(Unit))
        val localMovies = localMoviesEither.getOrHandle {
            when (it) {
                Failure.DatabaseFailure.ReadFailure.EmptyList -> emptyList()
            }
        }

        val categoriesEither =
            getRemoteMovieCategoriesUseCase.run(GetRemoteMovieCategoriesUseCase.Params(Unit))
        categoriesEither.fold(
            ifLeft = {
                return Either.Left(it)
            },
            ifRight = { categories ->
                categories.forEach { category ->
                    val remoteMoviesEither =
                        getRemoteMoviesByTypeUseCase.run(
                            GetRemoteMoviesByTypeUseCase.Params(
                                findCategoryTypeByString(category.type)  //todo check if findCategoryTypeByString should be called here or use a mapped model
                            )
                        )
//                    remoteMoviesEither.fold(
//                        ifLeft = { /*todo*/ },
//                        ifRight = { remoteMovies ->
//
//                        }
//                    )
                }
                return Either.Right(
                    categories.map {
                        it.toCategory(
                            movies =
                        )
                    }
                )
            }
        )
    }
}