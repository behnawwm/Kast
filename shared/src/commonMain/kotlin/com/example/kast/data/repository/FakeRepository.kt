package com.example.kast.data.repository

import com.example.kast.data.model.CategoryType
import com.example.kast.data.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeRepository(
//    private val apiServices: MovieService,
) {

    val categoryList = listOf(
        Category(CategoryType.Popular,"Movies"),
        Category(CategoryType.TopRated,"Movies"),
        Category(CategoryType.Upcoming,"Movies"),
        Category(CategoryType.NowPlaying,"Movies"),
    )

    fun getMovieCategories(): Flow<List<Category>>{
        return flow {
            delay(1500)
            emit(categoryList)
        }.flowOn(Dispatchers.Default)
    }

}