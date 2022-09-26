package com.example.kast

import kotlinx.coroutines.CoroutineScope

actual class MovieViewModel {
    actual val viewModelScope: CoroutineScope
        get() = TODO("Not yet implemented")

    actual fun getMovies() {
    }
}