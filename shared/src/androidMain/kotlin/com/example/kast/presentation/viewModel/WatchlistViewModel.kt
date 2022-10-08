package com.example.kast.presentation.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kast.data.repository.MovieRepositoryImpl
import com.example.kast.domain.model.MovieView
import com.example.kast.domain.repository.MovieRepository
import kotlinx.coroutines.launch

actual class WatchlistViewModel actual constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    data class State(
        val bookmarkedMovies: List<MovieView> = emptyList(),
    )

    var state = mutableStateOf(State())
        private set

    init {
        getBookmarkedMovies()
    }

    actual fun getBookmarkedMovies() {
        viewModelScope.launch {
            movieRepository.selectAllMovies().collect {
                state.value = state.value.copy(bookmarkedMovies = it)
            }
        }
    }

}