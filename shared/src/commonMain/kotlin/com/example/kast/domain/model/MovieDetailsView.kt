package com.example.kast.domain.model

import com.example.kast.data.source.remote.TmdbWebConfig.BASE_URL_TMDB_IMAGE
import com.example.kast.data.source.remote.model.TmdbGenre
import com.example.kast.utils.toDateString

data class MovieDetailsView(
    val id: Long,
    val title: String,
    val rating: Double?,
    val posterPath: String?,
    val budget: Long? = null,
    val overview: String? = null,
    val releaseDate: String? = null,
    val revenue: Long? = null,
    val genres: List<TmdbGenre>? = null,

    val isBookmarked: Boolean = false,
    val bookmarkDateTime: Long? = null,
    val isWatched: Boolean = false,
    val watchDateTime: Long? = null,
    val isCollected: Boolean = false,
    val collectDateTime: Long? = null,
) {

    val imageUrl: String? =
        if (posterPath != null)
            BASE_URL_TMDB_IMAGE + posterPath
        else
            null

    val bookmarkDate: String? = bookmarkDateTime?.toDateString()

    val watchDate: String? = watchDateTime?.toDateString()

    val collectDate: String? = collectDateTime?.toDateString()

}
