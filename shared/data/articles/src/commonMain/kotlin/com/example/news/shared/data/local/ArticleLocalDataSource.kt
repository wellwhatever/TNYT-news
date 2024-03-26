package com.example.news.shared.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOne
import com.example.news.ArticleDatabaseQueries
import com.example.news.shared.code.model.Article
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ArticleLocalDataSource(
    private val databaseQueries: ArticleDatabaseQueries,
    private val converter: ArticleEntityConverter,
    private val ioDispatcher: CoroutineDispatcher,
) {
    fun getArticleFlow(id: String): Flow<Article?> =
        databaseQueries.getArticle(id)
            .asFlow()
            .mapToOne(ioDispatcher)
            .map(converter::toDomain)

    suspend fun saveArticles(articles: List<Article>) {
        articles.map(converter::fromDomain).forEach { databaseQueries.insertArticle(it) }
    }
}
