package com.example.kast.presentation.viewModel

import com.example.kast.domain.usecase.GetMovieCategoriesUseCase
import com.example.kast.domain.usecase.GetMovieDetailsUseCase

expect class MovieDetailsViewModel actual constructor(
    getMovieDetailsUseCase: GetMovieDetailsUseCase,
    movieId:Long
) {

}