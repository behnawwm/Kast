package com.example.kast.presentation.viewModel

import com.example.kast.data.repository.MovieRepositoryImpl
import com.example.kast.domain.repository.MovieRepository
import com.example.kast.domain.usecase.InsertMovieUseCase

expect class AddToListsViewModel actual constructor(
   insertMovieUseCase: InsertMovieUseCase,
)