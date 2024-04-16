package com.example.news.shared.core.data

import com.example.news.shared.core.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    suspend fun getArticles(query: String): List<Article>

    fun getArticleFlow(id: String): Flow<Article?>
}
