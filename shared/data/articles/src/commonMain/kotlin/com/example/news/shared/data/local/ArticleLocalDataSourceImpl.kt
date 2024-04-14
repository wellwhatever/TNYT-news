package com.example.news.shared.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.news.ArticleDatabaseQueries
import com.example.news.shared.core.model.Article
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ArticleLocalDataSourceImpl(
    private val databaseQueries: ArticleDatabaseQueries,
    private val converter: ArticleEntityConverter,
    private val ioDispatcher: CoroutineDispatcher,
) : ArticleLocalDataSource {
    override fun getArticleFlow(id: String): Flow<Article?> =
        databaseQueries.getArticle(id)
            .asFlow()
            .mapToOneOrNull(ioDispatcher)
            .map { it?.let(converter::toDomain) }

    override suspend fun replaceArticles(articles: List<Article>) {
        databaseQueries.deleteAll()
        articles.map(converter::fromDomain).forEach { databaseQueries.insertArticle(it) }
    }
}
