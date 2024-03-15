package com.example.news.shared.data

import com.example.news.shared.code.data.ArticlesRepository
import com.example.news.shared.code.model.Article
import com.example.news.shared.data.remote.ArticlesRemoteDataSource

internal class OnlineFirstArticlesRepository(
    private val articlesRemoteDataSource: ArticlesRemoteDataSource
) : ArticlesRepository {
    override suspend fun getMostViewedArticles(
        period: Int
    ): List<Article> = articlesRemoteDataSource.getMostViewedArticles(period)
}