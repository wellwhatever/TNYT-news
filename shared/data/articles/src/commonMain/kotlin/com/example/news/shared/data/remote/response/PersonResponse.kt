package com.example.news.shared.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonResponse(
    @SerialName("firstname")
    val firstname: String?,
    @SerialName("lastname")
    val lastname: String?,
    @SerialName("middlename")
    val middlename: String?,
    @SerialName("organization")
    val organization: String?,
    @SerialName("rank")
    val rank: Int,
    @SerialName("role")
    val role: String,
)
