package com.example.news.shared.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeywordResponse(
    @SerialName("major")
    val major: String,
    @SerialName("name")
    val name: String,
    @SerialName("rank")
    val rank: Int,
    @SerialName("value")
    val value: String,
)
