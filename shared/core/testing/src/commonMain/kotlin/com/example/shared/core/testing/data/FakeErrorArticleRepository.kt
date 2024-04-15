package com.example.shared.core.testing.data

import com.example.news.shared.core.data.ArticleRepository
import com.example.news.shared.core.model.Article
import com.example.news.shared.core.network.NoInternetException
import kotlinx.coroutines.flow.Flow

class FakeErrorArticleRepository : ArticleRepository {

    override suspend fun getArticles(query: String): List<Article> {
        throw NoInternetException
    }

    override fun getArticleFlow(id: String): Flow<Article?> {
        throw NoInternetException
    }
}