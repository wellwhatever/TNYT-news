package com.example.news.feature.article.detail

interface ArticleDetailScreenActions {
    fun onBackClick()
    fun onRedirectToWebClick(url: String)

    fun onShareArticleClick(url: String)
}