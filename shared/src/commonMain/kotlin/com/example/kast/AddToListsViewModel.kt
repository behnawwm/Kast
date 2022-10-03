package com.example.kast

import com.example.kast.data.model.CategoryType
import com.example.kast.data.repository.FakeRepository
import com.example.kast.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope

expect class AddToListsViewModel actual constructor(
    movieRepository: MovieRepository,
) {
    val viewModelScope: CoroutineScope
}