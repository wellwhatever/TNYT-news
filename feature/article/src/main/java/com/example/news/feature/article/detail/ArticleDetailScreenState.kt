package com.example.news.feature.article.detail

import com.example.news.shared.code.model.Article

sealed interface ArticleDetailScreenState {
    data object Loading : ArticleDetailScreenState
    data class Error(val message: String) : ArticleDetailScreenState

    data class Content(
        val article: Article,
    ) : ArticleDetailScreenState
}
