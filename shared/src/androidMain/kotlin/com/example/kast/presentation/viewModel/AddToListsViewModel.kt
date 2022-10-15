package com.example.kast.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.handleError
import com.example.kast.domain.model.MovieView
import com.example.kast.domain.usecase.InsertMovieUseCase
import com.example.kast.presentation.mapper.toMovieView
import com.example.kast.utils.Failure
import com.example.kast.utils.getCurrentTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

actual class AddToListsViewModel actual constructor(
    private val insertMovieUseCase: InsertMovieUseCase,
) : ViewModel() {

    data class State(
        val error: String? = null,
        val movie: MovieView? = null,
    )

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()


    fun addMovieToBookmarked() {
        addMovieByType(MovieSaveType.Bookmarked)
    }

    fun removeMovieFromBookmarked() {
        removeMovieByType(MovieSaveType.Bookmarked)
    }

    fun addMovieToWatched() {
        addMovieByType(MovieSaveType.Watched)
    }

    fun removeMovieFromWatched() {
        removeMovieByType(MovieSaveType.Watched)
    }

    fun addMovieToCollected() {
        addMovieByType(MovieSaveType.Collected)
    }

    fun removeMovieFromCollected() {
        removeMovieByType(MovieSaveType.Collected)
    }

    fun setMovie(movie: MovieView) {
        _state.value = _state.value.copy(movie = movie)
    }

    private fun removeMovieByType(type: MovieSaveType) {
        val toSaveMovie = when (type) {
            MovieSaveType.Watched -> {
                state.value.movie!!.copy(
                    isWatched = false,
                    watchDateTime = null
                )
            }
            MovieSaveType.Bookmarked -> {
                state.value.movie!!.copy(
                    isBookmarked = false,
                    bookmarkDateTime = null
                )
            }
            MovieSaveType.Collected -> {
                state.value.movie!!.copy(
                    isCollected = false,
                    collectDateTime = null
                )
            }
        }
        insertMovie(toSaveMovie)
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
        insertMovie(toSaveMovie)
    }

    private fun insertMovie(toSaveMovie: MovieView) {
        insertMovieUseCase(InsertMovieUseCase.Params(toSaveMovie), viewModelScope) {
            it.fold(
                ifLeft = {
                    //todo
                },
                ifRight = { movie ->
                    _state.value = state.value.copy(movie = movie.toMovieView())
                }
            )
        }
    }

    sealed interface MovieSaveType {
        object Bookmarked : MovieSaveType
        object Watched : MovieSaveType
        object Collected : MovieSaveType
    }
}