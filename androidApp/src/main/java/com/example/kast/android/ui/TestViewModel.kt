package com.example.kast.android.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kast.android.data.Category
import com.example.kast.android.data.FakeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
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