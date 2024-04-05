package com.example.news.feature.article.detail

import androidx.compose.runtime.Immutable

@Immutable
interface ArticleDetailScreenActions {
    fun onBackClick()
    fun onRedirectToWebClick(url: String)
    fun onShareArticleClick(url: String)
}
