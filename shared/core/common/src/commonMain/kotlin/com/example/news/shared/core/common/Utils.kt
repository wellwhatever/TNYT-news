package com.example.news.shared.core.common

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Instant.toLocalDate(
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): LocalDate = this.toLocalDateTime(timeZone).date
