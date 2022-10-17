package com.example.kast.presentation.viewModel

import com.example.kast.domain.usecase.GetMovieCategoriesUseCase

expect class MovieViewModel actual constructor(
    getMovieCategoriesUseCase: GetMovieCategoriesUseCase
) {

}