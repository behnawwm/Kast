package com.example.kast.data.model

data class Category(
    val id: Long,
    val title: String,
    val subtitle:String,
    val movies: List<MovieView>,
    val deepLink: String = ""
)