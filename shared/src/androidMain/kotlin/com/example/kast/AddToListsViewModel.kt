package com.example.kast

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kast.data.model.*
import com.example.kast.data.repository.FakeRepository
import com.example.kast.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
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


    fun addMovieToWatchlist() {
        viewModelScope.launch {
            movieRepository.insertMovie(state.value.movie!!)
        }
    }

    fun setMovie(movie: MovieView) {
        state.value = state.value.copy(movie = movie)
    }

}


