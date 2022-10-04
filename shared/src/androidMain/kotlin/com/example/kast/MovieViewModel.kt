package com.example.kast

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kast.data.MoviesSource
import com.example.kast.data.model.*
import com.example.kast.data.repository.FakeRepository
import com.example.kast.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope as androidXViewModelScope


actual class MovieViewModel actual constructor(
    private val movieRepository: MovieRepository,
    private val fakeRepository: FakeRepository,
) : ViewModel() {
    actual val viewModelScope: CoroutineScope = androidXViewModelScope

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
        getMovies(CategoryType.Popular)

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

    lateinit var testMovies : Flow<PagingData<MovieView>>

    actual fun getMovies(type: CategoryType) {
//        movieRepository.getDynamicMovies(type.url).onEach { movies ->
//            remoteMovies.update { Pair(type, movies.orEmpty()) }
//        }.launchIn(viewModelScope)

        //test
        testMovies = Pager(PagingConfig(pageSize = 20)) {
            MoviesSource(movieRepository)
        }.flow
    }

    fun addMovieToWatchlist(movie: MovieView) {
        viewModelScope.launch {
            movieRepository.insertMovie(movie)
        }
    }


}


