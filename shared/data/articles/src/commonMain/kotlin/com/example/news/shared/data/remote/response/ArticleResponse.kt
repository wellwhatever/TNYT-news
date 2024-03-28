package com.example.news.shared.data.remote.response

import com.example.news.shared.core.common.serializer.InstantAsStringSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleResponse(
    @SerialName("abstract")
    val abstract: String,
    @SerialName("byline")
    val byline: BylineResponse,
    @SerialName("document_type")
    val documentType: String,
    @SerialName("_id")
    val id: String,
    @SerialName("keywords")
    val keywords: List<KeywordResponse>,
    @SerialName("lead_paragraph")
    val leadParagraph: String,
    @SerialName("multimedia")
    val multimedia: List<MultimediaResponse>,
    @SerialName("news_desk")
    val newsDesk: String? = null,
    @SerialName("pub_date")
    @Serializable(with = InstantAsStringSerializer::class)
    val pubDate: Instant,
    @SerialName("section_name")
    val sectionName: String? = null,
    @SerialName("snippet")
    val snippet: String,
    @SerialName("source")
    val source: String? = null,
    @SerialName("uri")
    val uri: String,
    @SerialName("web_url")
    val webUrl: String,
    @SerialName("word_count")
    val wordCount: Int,
)
