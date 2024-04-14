package com.example.shared.core.testing.data.remote

import com.example.news.shared.core.model.Article
import com.example.news.shared.data.remote.ArticleRemoteDataSource
import com.example.shared.core.testing.data.FakeArticleDataProvider

class FakeArticleRemoteDataSource : ArticleRemoteDataSource {
    override suspend fun getArticles(query: String): List<Article> =
        FakeArticleDataProvider.fakeArticles
}