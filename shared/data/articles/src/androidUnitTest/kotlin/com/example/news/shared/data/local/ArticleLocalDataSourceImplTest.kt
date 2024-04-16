package com.example.news.shared.data.local

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.news.ArticleDatabase
import com.example.shared.core.testing.data.FakeArticleDataProvider
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class ArticleLocalDataSourceImplTest : FunSpec({
    lateinit var database: ArticleDatabase
    lateinit var converter: ArticleEntityConverter
    lateinit var articleLocalDataSource: ArticleLocalDataSourceImpl

    beforeEach {
        database = ArticleDatabase(
            JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
                ArticleDatabase.Schema.create(this).await()
            },
        )
        converter = ArticleEntityConverter()
        articleLocalDataSource = ArticleLocalDataSourceImpl(
            database.articleDatabaseQueries,
            converter,
            UnconfinedTestDispatcher(),
        )
    }
    test("getArticleFlow should emit null for unknown article") {
        val result = articleLocalDataSource.getArticleFlow("4").firstOrNull()
        val expected = null

        result shouldBe expected
    }

    test("replaceArticles should cache articles") {
        val articles = FakeArticleDataProvider.fakeArticles
        articleLocalDataSource.replaceArticles(articles)

        val result = articleLocalDataSource.getArticleFlow("1").firstOrNull()
        val expected = FakeArticleDataProvider.fakeArticles.firstOrNull { it.id == "1" }

        result shouldBe expected
    }

    test("replaceArticles should delete all before saving new values") {
        val articles1 = FakeArticleDataProvider.fakeArticles.take(1)
        articleLocalDataSource.replaceArticles(articles1)
        val articles2 = FakeArticleDataProvider.fakeArticles.takeLast(2)
        articleLocalDataSource.replaceArticles(articles2)

        val result = articleLocalDataSource.getArticleFlow("1").firstOrNull()
        val expected = null

        result shouldBe expected
    }
})
