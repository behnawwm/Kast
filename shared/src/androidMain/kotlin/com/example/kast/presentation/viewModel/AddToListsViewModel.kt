package com.example.kast.presentation.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.handleError
import com.example.kast.domain.model.MovieView
import com.example.kast.domain.usecase.InsertMovieUseCase
import com.example.kast.utils.Failure
import com.example.kast.utils.getCurrentTime
import kotlinx.datetime.Clock

actual class AddToListsViewModel actual constructor(
    private val insertMovieUseCase: InsertMovieUseCase,
) : ViewModel() {

    data class State(
        val error: String? = null,
        val movie: MovieView? = null,
    )

    val state = mutableStateOf(State())


    fun addMovieToBookmarked() {
        addMovieByType(MovieSaveType.Bookmarked)
    }

    fun addMovieToWatched() {
        addMovieByType(MovieSaveType.Watched)
    }

    fun addMovieToCollected() {
        addMovieByType(MovieSaveType.Collected)
    }

    fun setMovie(movie: MovieView) {
        state.value = state.value.copy(movie = movie)
    }

    private fun addMovieByType(type: MovieSaveType) {
        val toSaveMovie = when (type) {
            MovieSaveType.Bookmarked -> {
                state.value.movie!!.copy(
                    isBookmarked = true,
                    bookmarkDateTime = getCurrentTime()
                )
            }
            MovieSaveType.Collected -> {
                state.value.movie!!.copy(
                    isCollected = true,
                    collectDateTime = getCurrentTime()
                )
            }
            MovieSaveType.Watched -> {
                state.value.movie!!.copy(
                    isWatched = true,
                    watchDateTime = getCurrentTime()
                )
            }
        }
        insertMovieUseCase(InsertMovieUseCase.Params(toSaveMovie), viewModelScope) {
            it.handleError {
                when (it) {
                    Failure.DatabaseFailure.InsertFailure -> {
                        //todo
                    }
                    else -> {}
                }
            }
        }

    }

    sealed interface MovieSaveType {
        object Bookmarked : MovieSaveType
        object Watched : MovieSaveType
        object Collected : MovieSaveType
    }
}