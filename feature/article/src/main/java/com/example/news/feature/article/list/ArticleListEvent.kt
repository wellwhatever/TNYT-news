package com.example.news.feature.article.list

sealed interface ArticleListEvent {
    data class NavigateToDetail(val articleId: String) : ArticleListEvent
}
