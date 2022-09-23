package com.example.kast.android

object FakeData {

    val movies = mutableListOf(
        Movie(0, "Thor Love and Thunder", 4.5f),
        Movie(1, "Batman", 4.3f),
        Movie(2, "Rop Gun: Maverick", 4.7f),
        Movie(3, "Ted 2", 3.5f),
        Movie(4, "Dragon Ball Super: Super Hero", 1.5f),
    )

    val categories = mutableListOf(
        Category(0, "Trending", movies.shuffled()),
        Category(1, "Popular", movies.shuffled()),
        Category(2, "New", movies.shuffled()),
        Category(3, "Top", movies.shuffled()),
    )
}

data class Movie(
    val id: Long,
    val title: String,
    val rating: Float,
    val imageUrl: String = ""
)

data class Category(
    val id: Long,
    val title: String,
    val movies: List<Movie>,
    val deepLink: String = ""
)