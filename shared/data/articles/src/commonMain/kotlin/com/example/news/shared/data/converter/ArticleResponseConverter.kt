package com.example.news.shared.data.converter

import com.example.news.shared.code.model.Article
import com.example.news.shared.core.common.toLocalDate
import com.example.news.shared.data.remote.response.ArticleResponse

internal class ArticleResponseConverter {
    fun toDomain(response: ArticleResponse) = with(response) {
        Article(
            id = id,
            title = abstract,
            publishedDate = pubDate.toLocalDate(),
            section = sectionName.orEmpty(),
            source = source,
        )
    }
}
