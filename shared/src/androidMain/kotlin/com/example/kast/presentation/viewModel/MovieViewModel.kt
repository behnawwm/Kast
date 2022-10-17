package com.example.kast.presentation.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kast.domain.model.*
import com.example.kast.domain.usecase.GetMovieCategoriesUseCase
import com.example.kast.presentation.mapper.toCategoryView
import kotlinx.coroutines.flow.*

actual class MovieViewModel actual constructor(
    private val getMovieCategoriesUseCase: GetMovieCategoriesUseCase,
) : ViewModel() {

    data class State(
        val error: String? = null,
        val categories: List<CategoryView> = emptyList(),
    )

    val state = mutableStateOf(State())

    init {
        getCategories()
    }

    private fun getCategories() {
        getMovieCategoriesUseCase(GetMovieCategoriesUseCase.Params(Unit)) {
            it.onEach {
                it.fold(
                    ifLeft = { failure ->
                        state.value = state.value.copy(
                            error = failure.toString()
                        )
                    },
                    ifRight = {
                        it.fold(
                            ifLeft = {
                                val (failure, list) = it
                                state.value = state.value.copy(
                                    categories = list.map { it.toCategoryView(failure.toString()) }
                                )
                            }, ifRight = {
                                state.value = state.value.copy(
                                    categories = it.map {
                                        it.toCategoryView(null)
                                    }
                                )
                            }
                        )

                    }
                )
            }.launchIn(viewModelScope)
        }

    }

}