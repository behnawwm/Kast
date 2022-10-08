package com.example.kast.presentation

import com.example.kast.data.repository.MovieRepositoryImpl

expect class AddToListsViewModel actual constructor(
    movieRepository: MovieRepositoryImpl,
)