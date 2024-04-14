package com.example.shared.core.testing.data

import com.example.news.shared.core.data.ArticleRepository
import com.example.news.shared.core.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeArticleRepository : ArticleRepository {

    private val articleFlow = MutableStateFlow<Article?>(null)
    override suspend fun getArticles(query: String): List<Article> =
        FakeArticleDataProvider.fakeArticles

    override fun getArticleFlow(id: String): Flow<Article?> = articleFlow

    suspend fun updateArticle(new: Article) {
        articleFlow.emit(new)
    }
}