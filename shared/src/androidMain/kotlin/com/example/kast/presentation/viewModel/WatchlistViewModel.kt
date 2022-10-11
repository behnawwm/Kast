package com.example.kast.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kast.data.repository.MovieRepositoryImpl
import com.example.kast.domain.model.MovieView
import com.example.kast.domain.repository.MovieRepository
import com.example.kast.domain.usecase.GetLocalMoviesUseCase
import com.example.kast.presentation.mapper.toMovieView
import com.example.kast.utils.Failure
import kotlinx.coroutines.launch

actual class WatchlistViewModel actual constructor(
    private val getLocalMoviesUseCase: GetLocalMoviesUseCase,
) : ViewModel() {

    data class State(
        val bookmarkedMovies: List<MovieView> = emptyList(),
        val databaseError: String? = null,
    )

    val state = mutableStateOf(State())

    init {
        getBookmarkedMovies()
    }

    actual fun getBookmarkedMovies() {
        getLocalMoviesUseCase(GetLocalMoviesUseCase.Params(Unit), viewModelScope) {
            it.fold(
                ifRight = {
                    state.value = state.value.copy(bookmarkedMovies = it.map { it.toMovieView() })
                },
                ifLeft = {
                    when (it) {
                        Failure.DatabaseFailure.ReadFailure.EmptyList -> {
                            state.value =
                                state.value.copy(databaseError = "You have no bookmarked movies!")
                        }
                        else -> {}
                    }
                }
            )
        }
    }

}