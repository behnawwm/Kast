package com.example.kast.domain.repository

import com.example.kast.domain.model.CategoryView
import kotlinx.coroutines.flow.Flow

interface MovieCategoryRepository {
    fun getMovieCategories(): Flow<List<CategoryView>>
}