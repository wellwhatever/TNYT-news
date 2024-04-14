package com.example.news.shared.domain.articles

import com.example.news.shared.core.data.ArticleRepository
import com.example.news.shared.core.model.Article
import kotlinx.coroutines.flow.Flow

class GetArticleUseCase(
    private val articleRepository: ArticleRepository,
) {
    operator fun invoke(id: String): Flow<Article?> =
        articleRepository.getArticleFlow(id)
}
