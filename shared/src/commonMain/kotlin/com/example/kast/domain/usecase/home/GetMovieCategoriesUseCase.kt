package com.example.kast.domain.usecase.home

import arrow.core.*
import com.example.kast.domain.mapper.findCategoryTypeByString
import com.example.kast.domain.mapper.toCategory
import com.example.kast.domain.model.Category
import com.example.kast.domain.usecase.GetLocalMoviesUseCase
import com.example.kast.domain.usecase.GetRemoteMovieCategoriesUseCase
import com.example.kast.domain.usecase.GetRemoteMoviesByTypeUseCase
import com.example.kast.utils.Failure
import com.example.kast.utils.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieCategoriesUseCase(
    private val getRemoteMovieCategoriesUseCase: GetRemoteMovieCategoriesUseCase,
    private val getRemoteMoviesByTypeUseCase: GetRemoteMoviesByTypeUseCase,
    private val getLocalMoviesUseCase: GetLocalMoviesUseCase,
) :
    FlowUseCase<List<Category>, GetMovieCategoriesUseCase.Params>() {

    data class Params(val none: Unit)

    override fun run(params: Params): Flow<Either<Failure.NetworkFailure, List<Category>>> {
        return flow {
            val localMovies =
                getLocalMoviesUseCase.run(GetLocalMoviesUseCase.Params(Unit)).getOrHandle {
                    when (it) {
                        Failure.DatabaseFailure.ReadFailure.EmptyList -> emptyList()
                    }
                }

            getRemoteMovieCategoriesUseCase.run(GetRemoteMovieCategoriesUseCase.Params(Unit)).fold(
                ifLeft = {
                    emit(Either.Left(it))
                },
                ifRight = { categories ->
                    val savedCategory = mutableListOf<Category>()
                    categories.map { category ->

                        getRemoteMoviesByTypeUseCase.run(
                            GetRemoteMoviesByTypeUseCase.Params(
                                findCategoryTypeByString(category.type)  //todo check if findCategoryTypeByString should be called here or use a mapped model
                            )
                        ).fold(
                            ifLeft = {
                                //todo
                                val newCategory = category.toCategory(emptyList())
                                savedCategory.add(newCategory)
                                emit(
                                    Either.Right(savedCategory
//                                        savedCategory.map {
//                                            if (it.id == category.id)
//                                                it.copy(movies = remoteMovies.map {
//                                                    it.toMovie(null //todo
////                                                        localMovies.find { it.id == remoteMovie.id }
//                                                    )
//                                                })
//                                            else
//                                                it
//
//                                        }
                                    )
                                )
                            },
                            ifRight = { remoteMovies ->
                                val newCategory = category.toCategory(
                                    remoteMovies.map { remoteMovie ->
                                        remoteMovie.toMovie(
                                            localMovies.find { it.id == remoteMovie.id }
                                        )
                                    }
                                )
                                savedCategory.add(newCategory)
                                emit(
                                    Either.Right(
                                        savedCategory.map {
                                            if (it.id == category.id)
                                                it.copy(movies = remoteMovies.map {
                                                    it.toMovie(null //todo
//                                                        localMovies.find { it.id == remoteMovie.id }
                                                    )
                                                })
                                            else
                                                it
                                        }
                                    )
                                )
                            }
                        )

                    }
                }
            )
        }

    }
}