package com.example.news.shared.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.plugin
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.isSuccess

class NetworkClient(
    private val baseUrl: String,
    httpClient: HttpClient,
) {
    val httpClientInternal = httpClient.config {
        defaultRequest {
            url(baseUrl)
        }
    }.apply {
        plugin(HttpSend).intercept { request ->
            // fixme For testing purposes only
            execute(request)
        }
    }

    suspend inline fun <reified T : Any> request(
        path: String,
        method: HttpMethod,
        requestBuilder: HttpRequestBuilder.() -> Unit = {},
    ): T {
        return safeCall {
            httpClientInternal.request(path) {
                this.method = method
                requestBuilder()
            }
        }
    }

    suspend inline fun <reified T : Any> safeCall(callHttpMethod: () -> HttpResponse): T {
        val response = mapApiExceptions { callHttpMethod() }
        return if (response.status.isSuccess()) {
            response.body()
        } else {
            throw response.toHttpException()
        }
    }

    inline fun mapApiExceptions(block: () -> HttpResponse): HttpResponse {
        return runCatching(block).getOrElse { exception ->
            throw when {
                exception.isNoInternet() -> NoInternetException()
                else -> UnexpectedException()
            }
        }
    }

    fun HttpResponse.toHttpException(): HttpExceptionDomain {
        return HttpExceptionDomain(code = status.value)
    }
}
