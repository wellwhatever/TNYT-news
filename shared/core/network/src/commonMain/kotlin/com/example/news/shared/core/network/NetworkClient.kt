package com.example.news.shared.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class NetworkClient(
    private val baseUrl: String,
    private val apiKey: String,
    httpClient: HttpClient,
) {
    val httpClientInternal = httpClient.config {
        install(Logging) {
            logger = Logger.Companion.DEFAULT
            level = LogLevel.ALL
            filter { request ->
                request.url.host.contains("ktor.io")
            }
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                },
            )
        }

        defaultRequest {
            url(baseUrl)
        }
    }.apply {
        plugin(HttpSend).intercept { request ->
            request.url {
                parameters.append("api-key", apiKey)
            }
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
