package com.example.kast.presentation.viewModel

import com.example.kast.data.repository.FakeMovieCategoryRepositoryImpl
import com.example.kast.data.repository.MovieRepositoryImpl
import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.repository.MovieCategoryRepository
import com.example.kast.domain.repository.MovieRepository
import com.example.kast.domain.usecase.home.GetMovieCategoriesUseCase
import kotlinx.coroutines.CoroutineScope

actual class MovieViewModel actual constructor(
    private val getMovieCategoriesUseCase: GetMovieCategoriesUseCase,
) {

}