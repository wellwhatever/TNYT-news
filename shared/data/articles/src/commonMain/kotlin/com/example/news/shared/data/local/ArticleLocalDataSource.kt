package com.example.news.shared.data.local

import com.example.news.shared.core.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleLocalDataSource {
    fun getArticleFlow(id: String): Flow<Article?>
    suspend fun replaceArticles(articles: List<Article>)
}