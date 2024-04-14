package com.example.news.shared.data.remote

import com.example.news.shared.core.model.Article
import com.example.news.shared.core.network.NetworkClient
import com.example.news.shared.data.remote.response.ArticleResponse
import com.example.news.shared.data.remote.response.ArticleResponseWrapper
import io.ktor.http.HttpMethod

internal class ArticleRemoteDataSourceImpl(
    private val networkClient: NetworkClient,
    private val articleConverter: ArticleResponseConverter,
) : ArticleRemoteDataSource {
    override suspend fun getArticles(query: String): List<Article> {
        val response: List<ArticleResponse> = networkClient.request<ArticleResponseWrapper>(
            path = ARTICLE_SEARCH_ROUTE,
            method = HttpMethod.Get,
        ) {
            url {
                parameters.append("query", query)
            }
        }.articleResponse.docs
        return response.map(articleConverter::toDomain)
    }

    companion object {
        const val ARTICLE_SEARCH_ROUTE = "svc/search/v2/articlesearch.json"
    }
}
