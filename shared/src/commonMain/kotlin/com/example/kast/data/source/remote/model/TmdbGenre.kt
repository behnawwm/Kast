package com.example.kast.data.source.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbGenre(
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?
)