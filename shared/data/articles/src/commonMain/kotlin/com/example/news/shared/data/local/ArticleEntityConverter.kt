package com.example.news.shared.data.local

import com.example.news.ArticleEntity
import com.example.news.shared.code.model.Article
import kotlinx.datetime.LocalDate

internal class ArticleEntityConverter {
    fun toDomain(entity: ArticleEntity): Article = with(entity) {
        Article(
            id = id,
            title = title,
            firstParagraph = firstParagraph,
            publishedDate = LocalDate.parse(publishedDate),
            section = section,
            source = source,
            imageUrl = imageUrl,
            webUrl = webUrl,
            desk = desk,
        )
    }

    fun fromDomain(domain: Article): ArticleEntity = with(domain) {
        ArticleEntity(
            id = id,
            title = title,
            firstParagraph = firstParagraph,
            publishedDate = publishedDate.toString(),
            section = section,
            source = source,
            imageUrl = imageUrl,
            webUrl = webUrl,
            desk = desk,
        )
    }
}
