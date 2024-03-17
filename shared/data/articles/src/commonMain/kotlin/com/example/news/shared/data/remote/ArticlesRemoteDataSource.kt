package com.example.news.shared.data.remote

import com.example.news.shared.code.model.Article
import com.example.news.shared.core.network.NetworkClient
import com.example.news.shared.data.converter.ArticleResponseConverter
import com.example.news.shared.data.remote.model.ArticleResponse
import com.example.news.shared.data.remote.model.ArticleResponseWrapper
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments

internal class ArticlesRemoteDataSource(
    private val networkClient: NetworkClient,
    private val articleConverter: ArticleResponseConverter,
) {
    suspend fun getMostViewedArticles(period: Int): List<Article> {
        val response: List<ArticleResponse> = networkClient.request<ArticleResponseWrapper>(
            path = MOST_VIEWED_ROUTE,
            method = HttpMethod.Get,
        ) {
            url {
                appendPathSegments("$period.json")
            }
        }.articleResponseWrappers
        return response.map(articleConverter::toDomain)
    }

    companion object {
        const val MOST_VIEWED_ROUTE = "svc/mostpopular/v2/viewed/"
    }
}