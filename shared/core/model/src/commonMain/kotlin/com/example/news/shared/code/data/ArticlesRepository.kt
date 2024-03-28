package com.example.news.shared.code.data

import com.example.news.shared.code.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {
    suspend fun getArticles(query: String): List<Article>

    fun getArticleFlow(id: String): Flow<Article?>
}
