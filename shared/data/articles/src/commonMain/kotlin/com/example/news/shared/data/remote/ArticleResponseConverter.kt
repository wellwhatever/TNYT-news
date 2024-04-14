package com.example.news.shared.data.remote

import com.example.news.shared.core.common.toLocalDate
import com.example.news.shared.core.model.Article
import com.example.news.shared.data.remote.response.ArticleResponse

internal class ArticleResponseConverter(
    private val tnytImagesBaseUrl: String,
) {
    fun toDomain(response: ArticleResponse) = with(response) {
        Article(
            id = id,
            title = abstract,
            publishedDate = pubDate.toLocalDate(),
            section = sectionName.orEmpty(),
            source = source.orEmpty(),
            imageUrl = "$tnytImagesBaseUrl${multimedia.firstOrNull()?.url.orEmpty()}",
            firstParagraph = leadParagraph,
            desk = newsDesk.orEmpty(),
            webUrl = webUrl,
        )
    }
}
