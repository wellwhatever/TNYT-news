package com.example.news.feature.article.list

import com.example.news.shared.core.network.DomainException
import com.example.news.shared.core.network.TooManyRequests

class ArticleListErrorMapper {
    fun mapToArticleListError(exception: DomainException): ArticleListState.Error =
        when (exception) {
            TooManyRequests -> ArticleListState.Error.TooManyRequests
            else -> ArticleListState.Error.Unexpected
        }
}
