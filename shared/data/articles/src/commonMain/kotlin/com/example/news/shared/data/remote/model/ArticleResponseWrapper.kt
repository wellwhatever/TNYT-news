package com.example.news.shared.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleResponseWrapper(
    @SerialName("copyright")
    val copyright: String,
    @SerialName("num_results")
    val numResults: Int,
    @SerialName("results")
    val articleResponseWrappers: List<ArticleResponse>,
    @SerialName("status")
    val status: String
)