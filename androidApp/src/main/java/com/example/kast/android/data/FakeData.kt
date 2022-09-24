package com.example.kast.android.data

object FakeData {

    val movies = mutableListOf(
        Movie(0, "Thor Love and Thunder", 4.5f),
        Movie(1, "Batman", 4.3f),
        Movie(2, "Rop Gun: Maverick", 4.7f),
        Movie(3, "Ted 2", 3.5f),
        Movie(4, "Dragon Ball Super: Super Hero", 1.5f),
    )

    val categories = mutableListOf(
        Category(0, "Trending","movies", movies.shuffled()),
        Category(1, "Popular","series", movies.shuffled()),
        Category(2, "New","movies", movies.shuffled()),
        Category(3, "Top", "movies", movies.shuffled()),
    )
}

data class Movie(
    val id: Long,
    val title: String,
    val rating: Float,
    val imageUrl: String = "https://c8.alamy.com/compde/pma8m3/von-marvel-avengers-alter-von-panasonic-poster-marvel-2015-poster-pma8m3.jpg"
)

data class Category(
    val id: Long,
    val title: String,
    val subtitle:String,
    val movies: List<Movie>,
    val deepLink: String = ""
)