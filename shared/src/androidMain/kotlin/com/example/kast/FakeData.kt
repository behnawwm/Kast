package com.example.kast

import com.example.kast.data.model.Category
import com.example.kast.data.model.MovieView

object FakeData {

    val sampleMovieList = mutableListOf(
        MovieView(0, "Thor Love and Thunder", 4.5, ""),
        MovieView(1, "Batman", 4.3, ""),
        MovieView(2, "Rop Gun: Maverick", 4.7, ""),
        MovieView(3, "Ted 2", 3.5, ""),
        MovieView(4, "Dragon Ball Super: Super Hero", 1.5, ""),
    )

    val categories = mutableListOf(
        Category(0, "Trending", "movies", sampleMovieList.shuffled()),
        Category(1, "Popular", "series", sampleMovieList.shuffled()),
        Category(2, "New", "movies", sampleMovieList.shuffled()),
        Category(3, "Top", "movies", sampleMovieList.shuffled()),
    )
}

