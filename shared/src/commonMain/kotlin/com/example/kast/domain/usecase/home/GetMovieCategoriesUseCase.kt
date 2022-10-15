package com.example.kast.domain.usecase.home

import arrow.core.*
import com.example.kast.domain.mapper.findCategoryTypeByString
import com.example.kast.domain.mapper.toCategory
import com.example.kast.domain.model.Category
import com.example.kast.domain.usecase.GetRemoteMovieCategoriesUseCase
import com.example.kast.domain.usecase.GetMoviesWithStatusByTypeUseCase
import com.example.kast.utils.Failure
import com.example.kast.utils.FlowUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class GetMovieCategoriesUseCase(
    private val getRemoteMovieCategoriesUseCase: GetRemoteMovieCategoriesUseCase,
    private val getMoviesWithStatusByTypeUseCase: GetMoviesWithStatusByTypeUseCase,
) :
    FlowUseCase<Either<Pair<Failure, List<Category>>, List<Category>>, GetMovieCategoriesUseCase.Params>() {

    data class Params(val none: Unit)

    override fun run(params: Params): Flow<Either<Failure.NetworkFailure, Either<Pair<Failure, List<Category>>, List<Category>>>> {
        return flow {
            getRemoteMovieCategoriesUseCase.run(GetRemoteMovieCategoriesUseCase.Params(Unit)).fold(
                ifLeft = {
                    emit(Either.Left(it))
                },
                ifRight = { categories ->
                    val savedCategory = mutableListOf<Category>()

                    val flows = categories.map { category ->
                        savedCategory.add(category.toCategory(null))
                        Pair(
                            category,
                            getMoviesWithStatusByTypeUseCase.run(
                                GetMoviesWithStatusByTypeUseCase.Params(
                                    findCategoryTypeByString(category.type)  //todo check if findCategoryTypeByString should be called here or use a mapped model
                                )
                            )
                        )
                    }

                    flows.instantCombinePair().collect {
                        val (category, flow) = it

                        flow.fold(
                            ifLeft = {
                                val newCategory = category.toCategory(null)
                                val index =
                                    savedCategory.indexOfFirst { it.id == newCategory.id }  // no checking for index == -1
                                savedCategory[index] =
                                    savedCategory[index].copy(movies = emptyList())
                                emit(Either.Right(Either.Left(Pair(it,savedCategory))))
                            },
                            ifRight = { remoteMovies ->
                                val newCategory = category.toCategory(remoteMovies)
                                val index =
                                    savedCategory.indexOfFirst { it.id == newCategory.id }  // no checking for index == -1
                                savedCategory[index] =
                                    savedCategory[index].copy(movies = remoteMovies)
                                emit(
                                    Either.Right(
                                        Either.Right(
                                            savedCategory
                                        )
                                    )
                                )
                            }
                        )

                    }
                }
            )
        }

    }

    private inline fun <reified T> List<Flow<T>>.instantCombine() = channelFlow {
        val array = Array(this@instantCombine.size) {
            false to (null as T?) // first element stands for "present"
        }

        this@instantCombine.forEachIndexed { index, flow ->
            launch {
                flow.collect { emittedElement ->
                    array[index] = true to emittedElement
                    send(array.filter { it.first }.map { it.second })
                }
            }
        }
    }

    private inline fun <reified T, K> List<Pair<K, Flow<T>>>.instantCombinePair() =
        channelFlow {
//        val array = Array(this@instantCombinePairSingle.size) {
//            false to (null as T?) // first element stands for "present"
//        }

            this@instantCombinePair.forEachIndexed { index, flow ->
                launch {
                    flow.second.collect { emittedElement ->
//                    array[index] = true to emittedElement
                        send(
                            Pair(
                                flow.first,
                                emittedElement
                            )
                        )
                    }
                }
            }
        }
}