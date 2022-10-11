package com.example.kast.presentation.mapper

import com.example.kast.domain.model.Category
import com.example.kast.domain.model.CategoryView
import com.example.kast.domain.model.Movie
import com.example.kast.domain.model.MovieView

fun Movie.toMovieView() = MovieView(
    id = id,
    title = title,
    rating = rating,
    posterPath = posterPath,
    isBookmarked = isBookmarked,
    bookmarkDateTime = bookmarkDateTime,
    isWatched = isWatched,
    watchDateTime = watchDateTime,
    isCollected = isCollected,
    collectDateTime = collectDateTime
)