package com.example.news.shared.data

import com.example.shared.core.testing.data.local.FakeArticleLocalDataSource
import com.example.shared.core.testing.data.remote.FakeArticleRemoteDataSource
import com.example.shared.core.testing.data.FakeArticleDataProvider
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first


class ArticleRepositoryTest : FunSpec({
    lateinit var remoteDataSource: FakeArticleRemoteDataSource
    lateinit var localDataSource: FakeArticleLocalDataSource
    lateinit var repository: ArticleRepositoryImpl

    beforeEach {
        remoteDataSource = FakeArticleRemoteDataSource()
        localDataSource = FakeArticleLocalDataSource()
        repository = ArticleRepositoryImpl(remoteDataSource, localDataSource)
    }

    test("getArticleFlow should call return item with right id") {
        val result = repository.getArticleFlow("1").first()
        val expected = FakeArticleDataProvider.article1
        result shouldBe expected
    }

    test("getArticleFlow should return null for unknown id") {
        val result = repository.getArticleFlow("123").first()
        val expected = null
        result shouldBe expected
    }

    test("getArticles should return values") {
        val result = repository.getArticles("")
        val expected = FakeArticleDataProvider.fakeArticles
        result shouldBe expected
    }
})