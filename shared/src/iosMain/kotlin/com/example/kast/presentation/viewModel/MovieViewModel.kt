package com.example.kast.presentation.viewModel

import com.example.kast.domain.usecase.GetMovieCategoriesUseCase

actual class MovieViewModel actual constructor(
    private val getMovieCategoriesUseCase: GetMovieCategoriesUseCase,
) {

}