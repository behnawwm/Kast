package com.example.kast.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kast.domain.mapper.toMovieDetailsView
import com.example.kast.domain.model.MovieDetailsView
import com.example.kast.domain.model.MovieView
import com.example.kast.domain.usecase.GetMovieDetailsUseCase
import com.example.kast.presentation.mapper.toMovieView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

actual class MovieDetailsViewModel actual constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    movieId: Long,
) : ViewModel() {

    data class State(
        val movie: MovieDetailsView? = null,
        val error: String? = null,
    )

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        getMovieDetails(movieId)
    }

    private fun getMovieDetails(movieId: Long) {
        getMovieDetailsUseCase(GetMovieDetailsUseCase.Params(movieId = movieId), viewModelScope) {
            it.fold(
                ifLeft = {
                    _state.value = _state.value.copy(error = it.toString())
                },
                ifRight = {
                    _state.value = _state.value.copy(movie = it.toMovieDetailsView())
                })
        }
    }

}