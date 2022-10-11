package com.example.kast.data.source.remote.model

import com.example.kast.MovieEntity
import com.example.kast.domain.model.Movie
import com.example.kast.domain.model.MovieView
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TmdbMovie(
    val adult: Boolean?,

    @SerialName("backdrop_path")
    val backdropPath: String?,

    @SerialName("genre_ids")
    val genreIds: List<Int>?,

    val id: Long,

    @SerialName("original_language")
    val originalLanguage: String?,

    @SerialName("original_title")
    val originalTitle: String?,

    val overview: String?,

    val popularity: Double?,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("release_date")
    val releaseDate: String?,

    val title: String?,

    val video: Boolean?,

    @SerialName("vote_average")
    val voteAverage: Double?,

    @SerialName("vote_count")
    val voteCount: Int?,
) {

    fun toMovie(localVersion: MovieEntity?): Movie {
        return Movie(
            id = id,
            title = originalTitle ?: "",
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
}