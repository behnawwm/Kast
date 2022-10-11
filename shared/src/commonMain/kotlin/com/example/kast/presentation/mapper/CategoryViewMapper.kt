package com.example.kast.presentation.mapper

import com.example.kast.domain.model.Category
import com.example.kast.domain.model.CategoryView

fun Category.toCategoryView() = CategoryView(
    type = type,
    subtitle = subtitle,
    movies = movies.map { it.toMovieView() },
    isLoading = false,
    errorString = if (movies.isEmpty()) "Error!@#$" else null
)