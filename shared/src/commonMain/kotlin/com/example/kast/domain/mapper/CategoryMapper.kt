package com.example.kast.domain.mapper

import com.example.kast.data.source.remote.model.TmdbCategory
import com.example.kast.domain.model.Category
import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.model.Movie

fun TmdbCategory.toCategory(movies: List<Movie>) = Category(
    findCategoryTypeByString(type),
    subtitle = subtitle,
    movies = movies
)

fun findCategoryTypeByString(type: String): CategoryType {
    return when (type) {
        CategoryType.NowPlaying.title -> CategoryType.NowPlaying
        CategoryType.Popular.title -> CategoryType.NowPlaying
        CategoryType.Upcoming.title -> CategoryType.NowPlaying
        CategoryType.TopRated.title -> CategoryType.NowPlaying
        else -> CategoryType.TopRated
    }
}