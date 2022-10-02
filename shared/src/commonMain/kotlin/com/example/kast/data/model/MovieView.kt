package com.example.kast.data.model

import com.example.kast.data.source.remote.TmdbWebConfig.BASE_URL_TMDB_IMAGE

@kotlinx.serialization.Serializable
data class MovieView(
    val id: Long,
    val title: String,
    val rating: Double?,
    val posterPath: String?,
    val tags : List<MovieTag> = emptyList()
) {
    val imageUrl: String? =
        if (posterPath != null)
            BASE_URL_TMDB_IMAGE + posterPath
        else
            null
}
