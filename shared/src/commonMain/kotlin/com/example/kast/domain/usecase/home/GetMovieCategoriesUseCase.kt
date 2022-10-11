package com.example.kast.domain.usecase.home

import arrow.core.*
import com.example.kast.domain.mapper.findCategoryTypeByString
import com.example.kast.domain.mapper.toCategory
import com.example.kast.domain.model.Category
import com.example.kast.domain.usecase.GetRemoteMovieCategoriesUseCase
import com.example.kast.domain.usecase.GetMoviesWithStatusByTypeUseCase
import com.example.kast.utils.Failure
import com.example.kast.utils.FlowUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class GetMovieCategoriesUseCase(
    private val getRemoteMovieCategoriesUseCase: GetRemoteMovieCategoriesUseCase,
    private val getMoviesWithStatusByTypeUseCase: GetMoviesWithStatusByTypeUseCase,
) :
    FlowUseCase<List<Category>, GetMovieCategoriesUseCase.Params>() {

    data class Params(val none: Unit)

    override fun run(params: Params): Flow<Either<Failure.NetworkFailure, List<Category>>> {
        return flow {
            getRemoteMovieCategoriesUseCase.run(GetRemoteMovieCategoriesUseCase.Params(Unit)).fold(
                ifLeft = {
                    emit(Either.Left(it))
                },
                ifRight = { categories ->
//                   async { useCases.getScheduleDetails(it).first() } .awaitAll()
                    val savedCategory = mutableListOf<Category>()
                    categories.forEach { category ->

                        val test = getMoviesWithStatusByTypeUseCase.run(
                            GetMoviesWithStatusByTypeUseCase.Params(
                                findCategoryTypeByString(category.type)  //todo check if findCategoryTypeByString should be called here or use a mapped model
                            )
                        ).onEach {
                            println("Raana :    ${category.type}")
                            it.fold(
                                ifLeft = {
                                    val newCategory = category.toCategory(emptyList())
                                    savedCategory.add(newCategory)
                                    emit(Either.Right(savedCategory))
                                },
                                ifRight = { remoteMovies ->
                                    val newCategory = category.toCategory(remoteMovies)
                                    savedCategory.add(newCategory)
                                    emit(
                                        Either.Right(
                                            savedCategory.map {
                                                if (it.id == category.id)
                                                    it.copy(movies = remoteMovies)
                                                else
                                                    it
                                            }
                                        )
                                    )
                                }
                            )
                        }.collect()
                    }
                }
            )
        }

    }

    fun <A> Collection<A>.forEachParallel(f: suspend (A) -> Unit): Unit = runBlocking {
        map { async() { f(it) } }.forEach { it.await() }
    }
}