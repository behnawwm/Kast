package com.example.kast.presentation.viewModel

import com.example.kast.domain.usecase.GetRemoteMovieCategoriesUseCase
import com.example.kast.domain.usecase.InsertMovieUseCase
import com.example.kast.domain.usecase.home.GetMovieCategoriesUseCase

expect class MovieViewModel actual constructor(
    insertMovieUseCase: InsertMovieUseCase,
    getMovieCategoriesUseCase: GetMovieCategoriesUseCase
) {

}