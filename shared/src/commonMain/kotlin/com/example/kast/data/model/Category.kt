package com.example.kast.data.model

data class Category(
    val type: CategoryType,
    val subtitle: String,
)

sealed class CategoryType(val title: String, val url: CategoryTypeUrl, val detailRoute: String) {
    object NowPlaying :
        CategoryType("Now Playing", CategoryTypeUrl.NowPlayingUrl, "home/more/now_playing")

    object Popular : CategoryType("Popular", CategoryTypeUrl.PopularUrl, "home/more/popular")
    object TopRated : CategoryType("Top Rated", CategoryTypeUrl.TopRatedUrl, "home/more/top_rated")
    object Upcoming : CategoryType("Upcoming", CategoryTypeUrl.UpcomingUrl, "home/more/upcoming")
}

sealed class CategoryTypeUrl(val url: String) {
    object NowPlayingUrl : CategoryTypeUrl("/now_playing")
    object PopularUrl : CategoryTypeUrl("/popular")
    object TopRatedUrl : CategoryTypeUrl("/top_rated")
    object UpcomingUrl : CategoryTypeUrl("/upcoming")
}

fun Category.toCategoryView() =
    CategoryView(type, subtitle, emptyList())
