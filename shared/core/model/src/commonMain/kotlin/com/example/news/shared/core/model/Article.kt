package com.example.news.shared.core.model

import kotlinx.datetime.LocalDate

data class Article(
    val id: String,
    val title: String,
    val firstParagraph: String,
    val publishedDate: LocalDate,
    val section: String,
    val source: String,
    val imageUrl: String,
    val webUrl: String,
    val desk: String,
)
