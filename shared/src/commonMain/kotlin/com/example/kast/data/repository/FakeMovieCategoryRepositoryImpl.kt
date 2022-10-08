package com.example.kast.data.repository

import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.model.Category
import com.example.kast.domain.repository.MovieCategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeMovieCategoryRepositoryImpl : MovieCategoryRepository {

    private val categoryList = listOf(
        Category(CategoryType.Popular, "Movies"),
        Category(CategoryType.TopRated, "Movies"),
        Category(CategoryType.Upcoming, "Movies"),
        Category(CategoryType.NowPlaying, "Movies"),
    )

    override fun getMovieCategories(): Flow<List<Category>> {
        return flow {
            delay(500)
            emit(categoryList)
        }.flowOn(Dispatchers.Default)
    }

}