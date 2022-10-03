package com.example.kast.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.toDateString(): String {
    val dateInstant = this.let { Instant.fromEpochMilliseconds(it) }
    return dateInstant.toLocalDateTime(TimeZone.UTC).date.toString()
}
