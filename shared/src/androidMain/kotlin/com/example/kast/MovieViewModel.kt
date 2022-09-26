package com.example.kast

import androidx.lifecycle.ViewModel
import com.example.kast.data.model.TmdbMovie
import com.example.kast.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope as androidXViewModelScope


actual class MovieViewModel actual constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    actual val viewModelScope: CoroutineScope = androidXViewModelScope
    val _movies: MutableStateFlow<List<TmdbMovie>> = MutableStateFlow(emptyList())
    val movies: StateFlow<List<TmdbMovie>> = _movies

    actual fun getMovies() {
        viewModelScope.launch {
            movieRepository.getPopularMovies(onSuccess = {
                _movies.update { it }
            }, onFailure = {

            })
        }
    }
}