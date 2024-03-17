package com.example.news.feature.article.list

import androidx.compose.runtime.Immutable

@Immutable
interface ArticleListActions {
    fun onArticleClick(articleId: String)
}