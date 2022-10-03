package com.example.kast.data.model

data class CategoryView(
    val type: CategoryType,
    val subtitle: String,
    val movies: List<MovieView>
)