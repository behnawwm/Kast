package com.example.kast

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kast.data.model.Category
import com.example.kast.data.model.TmdbMovie
import com.example.kast.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope as androidXViewModelScope


actual class MovieViewModel actual constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    val test = "sdsd"
    actual val viewModelScope: CoroutineScope = androidXViewModelScope

    data class State(
        val error: String? = null,
        val categories: List<Category> = emptyList(),
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
                        Category(
                            0,
                            "Trending",
                            "movies",
                            movies?.map { it.toMovie() } ?: emptyList()
                        ),
                        Category(
                            1,
                            "Popular",
                            "series",
                            movies?.map { it.toMovie() } ?: emptyList()
                        ),
                        Category(2, "New", "movies", movies?.map { it.toMovie() } ?: emptyList()),
                        Category(3, "Top", "movies", movies?.map { it.toMovie() } ?: emptyList()),
                    )
                )
            }
        }
    }


}