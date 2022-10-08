package com.example.kast.domain.model

data class CategoryView(
    val type: CategoryType,
    val subtitle: String,
    val movies: List<MovieView>,
)



