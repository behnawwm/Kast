package com.example.kast.presentation.viewModel

import com.example.kast.data.repository.MovieRepositoryImpl
import com.example.kast.domain.usecase.InsertMovieUseCase
import kotlinx.coroutines.CoroutineScope

actual class AddToListsViewModel actual constructor(
    insertMovieUseCase: InsertMovieUseCase,
) {

}