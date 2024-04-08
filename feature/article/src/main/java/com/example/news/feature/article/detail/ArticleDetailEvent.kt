package com.example.news.feature.article.detail

sealed interface ArticleDetailEvent {
    data class RedirectToWeb(val url: String) : ArticleDetailEvent
    data class ShareArticle(val articleUrl: String) : ArticleDetailEvent
    data object NavigateBack : ArticleDetailEvent
}
