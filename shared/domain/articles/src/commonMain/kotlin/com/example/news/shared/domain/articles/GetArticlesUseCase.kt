package com.example.news.shared.domain.articles

import com.example.news.shared.code.data.ArticlesRepository
import com.example.news.shared.code.model.Article

class GetArticlesUseCase(
    private val articlesRepository: ArticlesRepository,
) {
    suspend operator fun invoke(query: String): List<Article> =
        articlesRepository.getArticles(query)

    companion object {
        const val DEFAULT_ARTICLES_PERIOD = 7
    }
}
