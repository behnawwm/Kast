package com.example.kast.android

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {

    data class State(
        val error: String? = null,
        val categories: List<Category> = emptyList(),
    )

    val state = mutableStateOf(State())

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            delay(1000)
            state.value = state.value.copy(categories = FakeData.categories)
        }
    }
}