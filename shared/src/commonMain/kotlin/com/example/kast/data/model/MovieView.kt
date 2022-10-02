package com.example.kast.data.model

import com.example.kast.MovieEntity
import com.example.kast.data.source.remote.TmdbWebConfig.BASE_URL_TMDB_IMAGE

@kotlinx.serialization.Serializable
data class MovieView(
    val id: Long,
    val title: String,
    val rating: Double?,
    val posterPath: String?,
    val isBookmarked: Boolean = false,
    val bookmarkDate: String? = null,
    val isWatched: Boolean = false,
    val watchDate: String? = null,
    val isCollected: Boolean = false,
    val collectDate: String? = null,
) {
    val imageUrl: String? =
        if (posterPath != null)
            BASE_URL_TMDB_IMAGE + posterPath
        else
            null

    fun MovieView.toMovieEntity(): MovieEntity {
        return MovieEntity(id, title, rating, posterPath)
    }
}
