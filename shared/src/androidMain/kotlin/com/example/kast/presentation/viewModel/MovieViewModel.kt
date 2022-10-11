package com.example.kast.presentation.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kast.domain.model.*
import com.example.kast.domain.usecase.GetRemoteMovieCategoriesUseCase
import com.example.kast.domain.usecase.InsertMovieUseCase
import com.example.kast.domain.usecase.home.GetMovieCategoriesUseCase
import com.example.kast.presentation.mapper.toCategoryView
import com.example.kast.utils.Failure
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

actual class MovieViewModel actual constructor(
    private val insertMovieUseCase: InsertMovieUseCase,
    private val getMovieCategoriesUseCase: GetMovieCategoriesUseCase,
) : ViewModel() {

    data class State(
        val error: String? = null,
        val categories: List<CategoryView> = emptyList(),
    )

    val state = mutableStateOf(State())

//    private val savedMovies = MutableStateFlow(emptyList<MovieView>())
//    private val remoteMovies =
//        MutableStateFlow<CategoryUpdateData?>(null)

    init {
        test()
//        remoteMovies.combine(savedMovies) { remote, saved ->
//            val remoteCategory = remote?.category
//            val remoteMovies = remote?.movies ?: emptyList()
//
//            val prevCategories = state.value.categories
//
//            //todo check for better solutions
//            state.value = state.value.copy(
//                categories = prevCategories.map {
//                    val newMovies =
//                        if (it.type == remoteCategory)
//                            remoteMovies
//                        else
//                            it.movies
//                    it.copy(
//                        movies = newMovies.map { movie ->
//                            val savedVersion = saved.firstOrNull { it.id == movie.id }
//                            if (savedVersion != null)
//                                movie.copy(
//                                    isBookmarked = savedVersion.isBookmarked,
//                                    bookmarkDateTime = savedVersion.bookmarkDateTime,
//                                    isWatched = savedVersion.isWatched,
//                                    watchDateTime = savedVersion.watchDateTime,
//                                    isCollected = savedVersion.isCollected,
//                                    collectDateTime = savedVersion.collectDateTime
//                                )
//                            else
//                                movie
//                        },
//                        isLoading = false,
//                        errorString = remote?.errorMessage
//                    )
//                }
//            )
//        }.launchIn(viewModelScope)

//        getBookmarkedMovies()
//        getMovieCategories()
    }

    private fun test() {
        getMovieCategoriesUseCase(GetMovieCategoriesUseCase.Params(Unit)) {
            it.onEach {
                it.fold(
                    ifLeft = { failure ->
                        state.value = state.value.copy(
                            error = failure.toString()
                        )
                    },
                    ifRight = {
                        state.value = state.value.copy(
                            categories = it.map { it.toCategoryView() }
                        )
                    }
                )
            }.launchIn(viewModelScope)
        }

    }

//    private fun getBookmarkedMovies() {
//        viewModelScope.launch {
//            movieRepository.selectAllMovies().collectLatest {
//                it.fold(
//                    ifRight = { result ->
//                        savedMovies.update { result }
//                    },
//                    ifLeft = {
//                        //todo
//                    }
//                )
//            }
//        }
//    }

//    actual fun getMovieCategories() {
//        viewModelScope.launch {
//            movieCategoryRepository.getMovieCategories().collect { categoryList ->
//                state.value = state.value.copy(
//                    categories = categoryList
//                )
//                getMoviesForCategories(categoryList)
//            }
//        }
//    }

//    private fun getMoviesForCategories(categoryList: List<CategoryView>) {
//        categoryList.forEach {
//            getMoviesByType(it.type)
//        }
//    }

//    actual fun getMoviesByType(type: CategoryType) {
//        state.value = state.value.copy(
//            categories = state.value.categories.map {
//                if (it.type == type)
//                    it.copy(isLoading = true)
//                else
//                    it
//            }
//        )
//        viewModelScope.launch {
//            val result = movieRepository.getMoviesByType(type)
//            result.fold(
//                ifRight = { movies ->
//                    remoteMovies.update { CategoryUpdateData(type, movies, false, null) }
//                },
//                ifLeft = { error ->
//                    when (error) {
//                        is Failure.NetworkFailure.ClientException -> {
//                            remoteMovies.update {
//                                CategoryUpdateData(type,
//                                    emptyList(),
//                                    false,
//                                    error.exception.localizedMessage)
//                            }
//                        }
//                        is Failure.NetworkFailure.RedirectException -> {
//                            remoteMovies.update {
//                                CategoryUpdateData(type,
//                                    emptyList(),
//                                    false,
//                                    error.exception.localizedMessage)
//                            }
//                        }
//                        is Failure.NetworkFailure.ServerException -> {
//                            remoteMovies.update {
//                                CategoryUpdateData(type,
//                                    emptyList(),
//                                    false,
//                                    error.exception.localizedMessage)
//                            }
//                        }
//                        else -> {
//                            remoteMovies.update {
//                                CategoryUpdateData(type,
//                                    emptyList(),
//                                    false,
//                                    error.exception.localizedMessage)
//                            }
//                        }
//                    }
//                }
//            )
//        }
//    }

    data class CategoryUpdateData(
        val category: CategoryType,
        val movies: List<MovieView>,
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
    )

    fun addMovieToWatchlist(movie: MovieView) {
        viewModelScope.launch {
            insertMovieUseCase(InsertMovieUseCase.Params(movie), viewModelScope) {
                it.fold(ifLeft = {
                    //todo
                }, ifRight = {

                })
            }
        }
    }


}