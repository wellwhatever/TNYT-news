package com.example.news.shared.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleResponseWrapper(
    @SerialName("copyright")
    val copyright: String,
    @SerialName("response")
    val articleResponse: SearchResponse,
    @SerialName("status")
    val status: String,
)
