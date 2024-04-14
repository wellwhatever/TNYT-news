package com.example.news.shared.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
//
//class BaseNetworkClient(
//    private val baseUrl: String,
//    private val apiKey: String,
//    override val domainExceptionMapper: DomainExceptionMapper,
//    httpClient: HttpClient,
//) : NetworkClient {
//    override val httpClient = httpClient.config {
//        install(Logging) {
//            logger = Logger.Companion.DEFAULT
//            level = LogLevel.ALL
//            filter { request ->
//                request.url.host.contains("ktor.io")
//            }
//            sanitizeHeader { header -> header == HttpHeaders.Authorization }
//        }
//        install(ContentNegotiation) {
//            json(
//                Json {
//                    ignoreUnknownKeys = true
//                    prettyPrint = true
//                    isLenient = true
//                },
//            )
//        }
//
//        defaultRequest {
//            url(baseUrl)
//        }
//    }.apply {
//        plugin(HttpSend).intercept { request ->
//            request.url {
//                parameters.append("api-key", apiKey)
//            }
//            execute(request)
//        }
//    }
//}
