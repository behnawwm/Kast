package com.example.kast.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbPagingResult<T>(

    @SerialName("page")
    val page: Long?,

    @SerialName("results")
    val results: List<T>?,

    @SerialName("total_pages")
    val total_pages: Long?,

    @SerialName("total_results")
    val total_results: Long?,
)