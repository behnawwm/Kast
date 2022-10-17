package com.example.kast.presentation.viewModel

import com.example.kast.domain.usecase.GetMovieDetailsUseCase

actual class MovieDetailsViewModel actual constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val movieId: Long,
) {

}