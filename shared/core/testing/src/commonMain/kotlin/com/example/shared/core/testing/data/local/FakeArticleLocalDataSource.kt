package com.example.shared.core.testing.data.local

import com.example.news.shared.core.model.Article
import com.example.news.shared.data.local.ArticleLocalDataSource
import com.example.shared.core.testing.data.FakeArticleDataProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeArticleLocalDataSource : ArticleLocalDataSource {
    override fun getArticleFlow(id: String): Flow<Article?> =
        flowOf(
            FakeArticleDataProvider.fakeArticles.firstOrNull { it.id == id }
        )

    override suspend fun replaceArticles(articles: List<Article>) = Unit
}