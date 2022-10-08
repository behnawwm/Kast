package com.example.kast.presentation.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kast.data.repository.FakeMovieCategoryRepositoryImpl
import com.example.kast.data.repository.MovieRepositoryImpl
import com.example.kast.domain.model.*
import com.example.kast.domain.repository.MovieCategoryRepository
import com.example.kast.domain.repository.MovieRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

actual class MovieViewModel actual constructor(
    private val movieRepository: MovieRepository,
    private val fakeRepository: MovieCategoryRepository
) : ViewModel() {

    data class State(
        val error: String? = null,
        val categories: List<CategoryView> = emptyList(),
    )

    val state = mutableStateOf(State())

    private val savedMovies = MutableStateFlow(emptyList<MovieView>())
    private val remoteMovies =
        MutableStateFlow<Pair<CategoryType, List<MovieView>>>(
            Pair(
                CategoryType.Popular,
                emptyList()
            )
        )

    init {
        remoteMovies.combine(savedMovies) { remote, saved ->
            val (remoteCategory, remoteMovies) = remote

            val prevCategories = state.value.categories

            //todo check for better solutions
            state.value = state.value.copy(
                categories = prevCategories.map {
                    val newMovies =
                        if (it.type == remoteCategory)
                            remoteMovies
                        else
                            it.movies
                    it.copy(
                        movies = newMovies.map { movie ->
                            val savedVersion = saved.firstOrNull { it.id == movie.id }
                            if (savedVersion != null)
                                movie.copy(
                                    isBookmarked = savedVersion.isBookmarked,
                                    bookmarkDateTime = savedVersion.bookmarkDateTime,
                                    isWatched = savedVersion.isWatched,
                                    watchDateTime = savedVersion.watchDateTime,
                                    isCollected = savedVersion.isCollected,
                                    collectDateTime = savedVersion.collectDateTime
                                )
                            else
                                movie
                        }
                    )
                }
            )
        }.launchIn(viewModelScope)

        getBookmarkedMovies()
        getMovieCategories()
    }

    private fun getBookmarkedMovies() {
        viewModelScope.launch {
            movieRepository.selectAllMovies().collectLatest { movies ->
                savedMovies.update { movies }
            }
        }
    }

    actual fun getMovieCategories() {
        viewModelScope.launch {
            fakeRepository.getMovieCategories().collect { categoryList ->
                state.value = state.value.copy(
                    categories = categoryList
                )
                getMoviesForCategories(categoryList)
            }
        }
    }

    private fun getMoviesForCategories(categoryList: List<CategoryView>) {
        categoryList.forEach {
            getMovies(it.type)
        }
    }

    actual fun getMovies(type: CategoryType) {
        movieRepository.getDynamicMovies(type).onEach { movies ->
            remoteMovies.update { Pair(type, movies.orEmpty()) }
        }.launchIn(viewModelScope)
    }

    fun addMovieToWatchlist(movie: MovieView) {
        viewModelScope.launch {
            movieRepository.insertMovie(movie)
        }
    }


}