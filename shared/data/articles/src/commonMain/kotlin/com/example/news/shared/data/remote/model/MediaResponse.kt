package com.example.news.shared.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaResponse(
    @SerialName("approved_for_syndication")
    val approvedForSyndication: Int,
    @SerialName("caption")
    val caption: String,
    @SerialName("copyright")
    val copyright: String,
    @SerialName("media-metadata")
    val mediaMetadatumResponses: List<MediaMetadataResponse>,
    @SerialName("subtype")
    val subtype: String,
    @SerialName("type")
    val type: String
)