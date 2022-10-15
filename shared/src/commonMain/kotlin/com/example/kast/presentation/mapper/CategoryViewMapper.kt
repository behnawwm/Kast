package com.example.kast.presentation.mapper

import com.example.kast.domain.model.Category
import com.example.kast.domain.model.CategoryView

fun Category.toCategoryView(errorString: String?) = CategoryView(
    type = type,
    subtitle = subtitle,
    movies = movies.orEmpty().map { it.toMovieView() },
    isLoading = movies == null,
    errorString = if (movies?.isEmpty() == true) errorString else null
)