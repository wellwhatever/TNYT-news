package com.example.news.shared.data

import com.example.news.shared.code.data.ArticlesRepository
import com.example.news.shared.code.model.Article
import com.example.news.shared.data.local.ArticleLocalDataSource
import com.example.news.shared.data.remote.ArticleRemoteDataSource
import kotlinx.coroutines.flow.Flow

internal class ArticleRepositoryImpl(
    private val remoteDataSource: ArticleRemoteDataSource,
    private val localDataSource: ArticleLocalDataSource,
) : ArticlesRepository {
    override suspend fun getArticles(
        query: String,
    ): List<Article> = remoteDataSource.getArticles(query).also {
        localDataSource.saveArticles(it)
    }

    override fun getArticleFlow(id: String): Flow<Article?> =
        localDataSource.getArticleFlow(id)
}
