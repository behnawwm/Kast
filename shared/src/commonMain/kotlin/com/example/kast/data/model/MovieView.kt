package com.example.kast.data.model

import com.example.kast.data.source.remote.TmdbWebConfig.BASE_URL_TMDB_IMAGE

data class MovieView(
    val id: Long,
    val title: String,
    val rating: Float,
    val posterPath: String = "",
    val imageUrl: String = BASE_URL_TMDB_IMAGE + posterPath
)
