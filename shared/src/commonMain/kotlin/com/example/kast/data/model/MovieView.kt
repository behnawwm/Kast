package com.example.kast.data.model

import com.example.kast.data.source.remote.TmdbWebConfig.BASE_URL_TMDB_IMAGE
import com.example.kast.utils.toDateString

@kotlinx.serialization.Serializable
data class MovieView(
    val id: Long,
    val title: String,
    val rating: Double?,
    val posterPath: String?,
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
