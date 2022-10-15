package com.example.kast.domain.usecase

import arrow.core.Either
import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.model.Movie
import com.example.kast.domain.repository.MovieRepository
import com.example.kast.utils.Failure
import com.example.kast.utils.FlowUseCase
import kotlinx.coroutines.flow.Flow

class GetMoviesWithStatusByTypeUseCase(private val movieRepository: MovieRepository) :
    FlowUseCase<List<Movie>, GetMoviesWithStatusByTypeUseCase.Params>() {

    data class Params(val categoryType: CategoryType)

    override fun run(params: Params): Flow<Either<Failure, List<Movie>>> {
        return movieRepository.getMoviesWithStatusByTypeAsFlow(params.categoryType)
    }

}