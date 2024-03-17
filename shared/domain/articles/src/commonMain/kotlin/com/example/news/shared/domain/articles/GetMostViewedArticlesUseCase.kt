package com.example.news.shared.domain.articles

import com.example.news.shared.code.data.ArticlesRepository
import com.example.news.shared.code.model.Article

class GetMostViewedArticlesUseCase(
    private val articlesRepository: ArticlesRepository
) {
    suspend operator fun invoke(period: Int = DEFAULT_ARTICLES_PERIOD): List<Article> =
        articlesRepository.getMostViewedArticles(period)

    companion object {
        const val DEFAULT_ARTICLES_PERIOD = 7
    }
}