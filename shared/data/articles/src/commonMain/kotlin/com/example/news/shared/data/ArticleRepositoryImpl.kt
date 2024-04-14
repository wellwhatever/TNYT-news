package com.example.news.shared.data

import com.example.news.shared.core.data.ArticleRepository
import com.example.news.shared.core.model.Article
import com.example.news.shared.data.local.ArticleLocalDataSource
import com.example.news.shared.data.remote.ArticleRemoteDataSource
import kotlinx.coroutines.flow.Flow

internal class ArticleRepositoryImpl(
    private val remoteDataSource: ArticleRemoteDataSource,
    private val localDataSource: ArticleLocalDataSource,
) : ArticleRepository {
    override suspend fun getArticles(
        query: String,
    ): List<Article> = remoteDataSource.getArticles(query).also {
        localDataSource.replaceArticles(it)
    }

    override fun getArticleFlow(id: String): Flow<Article?> =
        localDataSource.getArticleFlow(id)
}
