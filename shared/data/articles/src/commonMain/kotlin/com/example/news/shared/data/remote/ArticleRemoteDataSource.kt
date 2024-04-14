package com.example.news.shared.data.remote

import com.example.news.shared.core.model.Article

interface ArticleRemoteDataSource {
    suspend fun getArticles(query: String): List<Article>
}
