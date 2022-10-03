package com.example.kast

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kast.data.model.*
import com.example.kast.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import androidx.lifecycle.viewModelScope as androidXViewModelScope


actual class AddToListsViewModel actual constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    actual val viewModelScope: CoroutineScope = androidXViewModelScope

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


