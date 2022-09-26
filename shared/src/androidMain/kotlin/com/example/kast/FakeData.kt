package com.example.kast

import com.example.kast.data.model.Category
import com.example.kast.data.model.MovieView

object FakeData {

    val movies = mutableListOf(
        MovieView(0, "Thor Love and Thunder", 4.5f),
        MovieView(1, "Batman", 4.3f),
        MovieView(2, "Rop Gun: Maverick", 4.7f),
        MovieView(3, "Ted 2", 3.5f),
        MovieView(4, "Dragon Ball Super: Super Hero", 1.5f),
    )

    val categories = mutableListOf(
        Category(0, "Trending","movies", movies.shuffled()),
        Category(1, "Popular","series", movies.shuffled()),
        Category(2, "New","movies", movies.shuffled()),
        Category(3, "Top", "movies", movies.shuffled()),
    )
}

