package com.example.kast.domain.model

data class Category(
    val type: CategoryType,
    val subtitle: String,
    val movies: List<Movie>,
)



