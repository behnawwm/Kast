package com.example.kast.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kast.domain.model.MovieView
import com.example.kast.domain.usecase.GetLocalMoviesUseCase
import com.example.kast.presentation.mapper.toMovieView
import com.example.kast.utils.Failure
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

actual class WatchlistViewModel actual constructor(
    private val getLocalMoviesUseCase: GetLocalMoviesUseCase,
) : ViewModel() {

    data class State(
        val watchlistTabData: List<String> = emptyList(),
        val historyTabData: List<String> = emptyList(),
        val allMovies: List<MovieView> = emptyList(),
        val databaseError: String? = null,
    )

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        getCategorySectionsData()
        getBookmarkedMovies()
    }

    private fun getCategorySectionsData() {
        getWatchlistSections()
        getHistorySections()
    }

    private fun getHistorySections() {
        _state.value = _state.value.copy(historyTabData = listOf(
            "Movies", "TV Shows", "Episodes"
        ))
    }

    private fun getWatchlistSections() {
        _state.value = _state.value.copy(watchlistTabData = listOf(
            "Bookmarked", "Watched", "Collection"
        ))
    }

    actual fun getBookmarkedMovies() {
        getLocalMoviesUseCase(GetLocalMoviesUseCase.Params(Unit), viewModelScope) {
            it.fold(
                ifRight = {
                    _state.value = state.value.copy(allMovies = it.map { it.toMovieView() })
                },
                ifLeft = {
                    when (it) {
                        Failure.DatabaseFailure.ReadFailure.EmptyList -> {
                            _state.value =
                                state.value.copy(databaseError = "You have no bookmarked movies!")
                        }
                        else -> {}
                    }
                }
            )
        }
    }

}