package com.example.kast.data.repository

import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.model.CategoryView
import com.example.kast.domain.repository.MovieCategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeMovieCategoryRepositoryImpl : MovieCategoryRepository {

    private val categoryList = listOf(
        CategoryView(CategoryType.Popular, "Movies", emptyList()),
        CategoryView(CategoryType.TopRated, "Movies", emptyList()),
        CategoryView(CategoryType.Upcoming, "Movies", emptyList()),
        CategoryView(CategoryType.NowPlaying, "Movies", emptyList()),
    )

    override fun getMovieCategories(): Flow<List<CategoryView>> {
        return flow {
            delay(500)
            emit(categoryList)
        }.flowOn(Dispatchers.Default)
    }

}