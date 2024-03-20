package com.example.news.shared.code.data

import com.example.news.shared.code.model.Article

interface ArticlesRepository {
    suspend fun getArticles(query: String): List<Article>

    companion object {
        const val NEW_YORK_TIMES_BASE_URL = "https://api.nytimes.com/"
    }
}
