package com.example.news.shared.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BylineResponse(
    @SerialName("organization")
    val organization: String?,
    @SerialName("original")
    val original: String?,
    @SerialName("person")
    val person: List<PersonResponse>,
)
