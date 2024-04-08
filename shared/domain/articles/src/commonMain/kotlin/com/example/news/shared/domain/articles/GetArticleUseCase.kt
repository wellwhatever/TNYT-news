package com.example.news.shared.domain.articles

import com.example.news.shared.code.data.ArticlesRepository
import com.example.news.shared.code.model.Article
import kotlinx.coroutines.flow.Flow

class GetArticleUseCase(
    private val articleRepository: ArticlesRepository,
) {
    operator fun invoke(id: String): Flow<Article?> =
        articleRepository.getArticleFlow(id)
}
