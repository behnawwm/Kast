package com.example.kast

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kast.data.model.*
import com.example.kast.data.repository.FakeRepository
import com.example.kast.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope as androidXViewModelScope


actual class MovieViewModel actual constructor(
    private val movieRepository: MovieRepository,
    private val fakeRepository: FakeRepository
) : ViewModel() {
    actual val viewModelScope: CoroutineScope = androidXViewModelScope

    data class State(
        val error: String? = null,
        val categories: List<CategoryView> = emptyList(),
    )

    val state = mutableStateOf(State())

    private val _bookmarkedMovies = MutableStateFlow(emptyList<MovieView>())
    private val _remoteMovies =
        MutableStateFlow<Pair<CategoryType, List<MovieView>>>(
            Pair(
                CategoryType.Popular,
                emptyList()
            )
        )

    init {
        _remoteMovies.combine(_bookmarkedMovies) { remote, bookmarked ->
            val (remoteCategory, remoteMovies) = remote

            val modifiedMovies = remoteMovies.map { movie ->
                movie.copy(isBookmarked = bookmarked.any { it.id == movie.id })
            }

            val prevCategories = state.value.categories

            //todo check for better solutions
            state.value = state.value.copy(
                categories = prevCategories.map {
                    if (it.type == remoteCategory)
                        it.copy(movies = modifiedMovies)
                    else
                        it.apply {
                            it.movies.map { movie ->
                                movie.copy(isBookmarked = bookmarked.any { it.id == movie.id })
                            }
                        }
                }
            )
        }.launchIn(viewModelScope)

        getBookmarkedMovies()
        getMovieCategories()
    }

    private fun getBookmarkedMovies() {
        viewModelScope.launch {
            movieRepository.selectAllMovies().collectLatest { movies ->
                _bookmarkedMovies.update { movies }
            }
        }
    }

    actual fun getMovieCategories() {
        viewModelScope.launch {
            fakeRepository.getMovieCategories().collect { categoryList ->
                state.value = state.value.copy(
                    categories = categoryList.map { it.toCategoryView() }
                )
                getMoviesForCategories(categoryList)
            }
        }
    }

    private fun getMoviesForCategories(categoryList: List<Category>) {
        categoryList.forEach {
            getMovies(it.type)
        }
    }

    actual fun getMovies(type: CategoryType) {
        viewModelScope.launch {
            movieRepository.getDynamicMovies(type.url).collect { movies ->
                _remoteMovies.update { Pair(type, movies.orEmpty()) }
//                val prevCategories = state.value.categories
//                state.value = state.value.copy(
//                    categories = prevCategories.map {
//                        if (it.type == type)
//                            it.copy(movies = movies.orEmpty())
//                        else
//                            it
//                    }
//                )
            }
        }
    }

    fun addMovieToWatchlist(movie: MovieView) {
        viewModelScope.launch {
            movieRepository.insertMovie(movie)
        }
    }


}


