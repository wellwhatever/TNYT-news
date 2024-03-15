package com.example.news.shared.code.data

import com.example.news.shared.code.model.Article

interface ArticlesRepository {
    suspend fun getMostViewedArticles(period: Int): List<Article>

    companion object {
        const val NEW_YORK_TIMES_BASE_URL = "https://api.nytimes.com/"
    }
}