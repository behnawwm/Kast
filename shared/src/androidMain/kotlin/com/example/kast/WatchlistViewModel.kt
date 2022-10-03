package com.example.kast

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kast.data.model.CategoryType
import com.example.kast.data.model.MovieView
import com.example.kast.data.repository.FakeRepository
import com.example.kast.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope as androidXViewModelScope

actual class WatchlistViewModel actual constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    actual val viewModelScope: CoroutineScope = androidXViewModelScope

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