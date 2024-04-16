package com.example.news.shared.domain.articles

import com.example.news.shared.core.data.ArticleRepository
import com.example.news.shared.core.model.Article

class GetArticlesUseCase(
    private val articlesRepository: ArticleRepository,
) {
    suspend operator fun invoke(query: String): List<Article> =
        articlesRepository.getArticles(query)
}
