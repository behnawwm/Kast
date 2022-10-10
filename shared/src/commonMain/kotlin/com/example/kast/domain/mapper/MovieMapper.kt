package com.example.kast.domain.mapper

import com.example.kast.MovieEntity
import com.example.kast.domain.model.Movie
import com.example.kast.domain.model.MovieView

fun MovieEntity.toMovie(): Movie {
    return Movie(
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

fun Movie.toMovieEntity(): MovieEntity {
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