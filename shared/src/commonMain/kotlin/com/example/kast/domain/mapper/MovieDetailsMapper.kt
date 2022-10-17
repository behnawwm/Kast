package com.example.kast.domain.mapper

import com.example.kast.MovieEntity
import com.example.kast.data.source.remote.model.TmdbMovieDetails
import com.example.kast.domain.model.MovieDetails
import com.example.kast.domain.model.MovieDetailsView

fun TmdbMovieDetails.toMovieDetails(localVersion: MovieEntity?): MovieDetails {
    return MovieDetails(
        id = id,
        title = title ?: "",
        rating = voteAverage,
        posterPath = posterPath ?: "",
        revenue = revenue,
        budget = budget,
        overview = overview,
        genres = genres,
        isBookmarked = localVersion?.isBookmarked ?: false,
        bookmarkDateTime = localVersion?.bookmarkDateTime,
        isWatched = localVersion?.isWatched ?: false,
        watchDateTime = localVersion?.watchDateTime,
        isCollected = localVersion?.isCollected ?: false,
        collectDateTime = localVersion?.collectDateTime,
    )
}

fun MovieDetails.toMovieDetailsView(): MovieDetailsView {
    return MovieDetailsView(
        id = id,
        title = title,
        rating = rating,
        posterPath = posterPath,
        revenue = revenue,
        budget = budget,
        overview = overview,
        genres = genres,
        isBookmarked = isBookmarked,
        bookmarkDateTime = bookmarkDateTime,
        isWatched = isWatched,
        watchDateTime = watchDateTime,
        isCollected = isCollected,
        collectDateTime = collectDateTime,
    )
}