package com.example.kast.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kast.domain.model.MovieView
import com.example.kast.data.repository.MovieRepositoryImpl
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

actual class AddToListsViewModel actual constructor(
    private val movieRepository: MovieRepositoryImpl
) : ViewModel() {

    data class State(
        val error: String? = null,
        val movie: MovieView? = null,
    )

    val state = mutableStateOf(State())

    fun addMovieToBookmarked() {
        viewModelScope.launch {
            movieRepository.insertMovie(
                state.value.movie!!.copy(
                    isBookmarked = true,
                    bookmarkDateTime = Clock.System.now().toEpochMilliseconds()
                )
            )
        }
    }

    fun addMovieToWatched() {
        viewModelScope.launch {
            movieRepository.insertMovie(
                state.value.movie!!.copy(
                    isWatched = true,
                    watchDateTime = Clock.System.now().toEpochMilliseconds()
                )
            )
        }
    }

    fun addMovieToCollections() {
        viewModelScope.launch {
            movieRepository.insertMovie(
                state.value.movie!!.copy(
                    isCollected = true,
                    collectDateTime = Clock.System.now().toEpochMilliseconds()
                )
            )
        }
    }

    fun setMovie(movie: MovieView) {
        state.value = state.value.copy(movie = movie)
    }

}