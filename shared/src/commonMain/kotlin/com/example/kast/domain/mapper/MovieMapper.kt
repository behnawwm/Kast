package com.example.kast.domain.mapper

import com.example.kast.MovieEntity
import com.example.kast.data.source.remote.model.TmdbMovie
import com.example.kast.data.source.remote.model.TmdbMovieDetails
import com.example.kast.domain.model.Movie
import com.example.kast.domain.model.MovieDetails
import com.example.kast.domain.model.MovieDetailsView
import com.example.kast.domain.model.MovieView

fun TmdbMovie.toMovie(localVersion: MovieEntity?): Movie {
    return Movie(
        id = id,
        title = title ?: "",
        rating = voteAverage,
        posterPath = posterPath ?: "",
        isBookmarked = localVersion?.isBookmarked ?: false,
        bookmarkDateTime = localVersion?.bookmarkDateTime,
        isWatched = localVersion?.isWatched ?: false,
        watchDateTime = localVersion?.watchDateTime,
        isCollected = localVersion?.isCollected ?: false,
        collectDateTime = localVersion?.collectDateTime,
    )
}

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
}

fun MovieView.toMovie() =
    Movie(
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