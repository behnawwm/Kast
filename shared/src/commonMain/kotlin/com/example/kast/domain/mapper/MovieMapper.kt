package com.example.kast.domain.mapper

import com.example.kast.MovieEntity
import com.example.kast.domain.model.MovieView

fun MovieEntity.toMovieView(): MovieView {
    return MovieView(
        id,
        title,
        rating,
        posterPath,
        isBookmarked,
        bookmarkDateTime,
        isWatched,
        watchDateTime,
        isCollected,
        collectDateTime
    )
}

fun MovieView.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id,
        title,
        rating,
        posterPath,
        isBookmarked,
        bookmarkDateTime,
        isWatched,
        watchDateTime,
        isCollected,
        collectDateTime
    )
}