package com.example.news.shared.data.converter

import com.example.news.shared.code.model.Article
import com.example.news.shared.core.common.toLocalDate
import com.example.news.shared.data.remote.model.ArticleResponse

internal class ArticleResponseConverter {
    fun toDomain(response: ArticleResponse) = with(response) {
        Article(
            id = id.toString(),
            title = title,
            publishedDate = publishedDate.toLocalDate(),
            section = section,
            source = source,
        )
    }
}