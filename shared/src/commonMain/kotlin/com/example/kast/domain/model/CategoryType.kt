package com.example.kast.domain.model

sealed class CategoryType(val title: String, val url: String, val detailRoute: String) {
    object NowPlaying :
        CategoryType("Now Playing", "now_playing", "home/more/now_playing")

    object Popular : CategoryType("Popular", "popular", "home/more/popular")
    object TopRated : CategoryType("Top Rated", "top_ratedd", "home/more/top_rated")
    object Upcoming : CategoryType("Upcoming", "upcoming", "home/more/upcoming")
}