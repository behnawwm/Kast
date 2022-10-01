package com.example.kast

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kast.data.model.Category
import com.example.kast.data.model.CategoryType
import com.example.kast.data.model.CategoryView
import com.example.kast.data.model.toCategoryView
import com.example.kast.data.repository.FakeRepository
import com.example.kast.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
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

    init {
        getMovieCategories()
    }

    actual fun getMovieCategories() {
        viewModelScope.launch {
            fakeRepository.getMovieCategories().collect { categoryList ->
                getMoviesForCategories(categoryList)
                state.value = state.value.copy(
                    categories = categoryList.map { it.toCategoryView() }
                )
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
                val prevCategories = state.value.categories
                state.value = state.value.copy(
                    categories = prevCategories.map {
                        if (it.type == type)
                            it.copy(movies = movies.orEmpty().map { it.toMovie() })
                        else
                            it
                    }
                )
            }
        }
    }


}


