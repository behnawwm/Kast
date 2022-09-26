package com.example.kast.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbMovieResult(

    @SerialName("page")
    val page: Long,
    @SerialName("results")
    val results: List<TmdbMovie>?,
    @SerialName("total_pages")
    val total_pages: Long,
    @SerialName("total_results")
    val total_results: Long,
)