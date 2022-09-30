package com.example.kast

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kast.data.model.CategoryView
import com.example.kast.data.model.TmdbMovie
import com.example.kast.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope as androidXViewModelScope


actual class MovieViewModel actual constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    actual val viewModelScope: CoroutineScope = androidXViewModelScope

    data class State(
        val error: String? = null,
        val categories: List<CategoryView> = emptyList(),
        val movies: List<TmdbMovie> = emptyList()
    )

    val state = mutableStateOf(State())

    init {
        getMovies()
    }


    actual fun getMovies() {
        viewModelScope.launch {
            movieRepository.getPopularMovies().collect { movies ->
                state.value = state.value.copy(
                    categories = mutableListOf(
                        CategoryView(
                            0,
                            "Trending",
                            "movies",
                            movies?.map { it.toMovie() } ?: emptyList()
                        ),
                        CategoryView(
                            1,
                            "Popular",
                            "series",
                            movies?.map { it.toMovie() } ?: emptyList()
                        ),
                        CategoryView(2, "New", "movies", movies?.map { it.toMovie() } ?: emptyList()),
                        CategoryView(3, "Top", "movies", movies?.map { it.toMovie() } ?: emptyList()),
                    )
                )
            }
        }
    }


}