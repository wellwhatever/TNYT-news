package com.example.news.shared.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MultimediaResponse(
    @SerialName("crop_name")
    val cropName: String,
    @SerialName("height")
    val height: Int,
    @SerialName("rank")
    val rank: Int,
    @SerialName("subType")
    val subType: String,
    @SerialName("subtype")
    val subtype: String,
    @SerialName("type")
    val type: String,
    @SerialName("url")
    val url: String,
    @SerialName("width")
    val width: Int,
)
