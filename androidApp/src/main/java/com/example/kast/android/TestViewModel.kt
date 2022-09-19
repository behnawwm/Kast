package com.example.kast.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            kotlin.runCatching {
                com.example.kast.Greeting().greeting()
            }.onSuccess { data ->
                _message.update {
                    data
                }
            }.onFailure { error ->
                _message.update {
                    error.localizedMessage
                }
            }

        }
    }
}