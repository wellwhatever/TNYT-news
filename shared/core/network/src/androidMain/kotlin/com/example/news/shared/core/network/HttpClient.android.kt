package com.example.news.shared.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.Logging
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

private val defaultConnectionTimeout = 10.seconds

internal fun provideHttpClient() = HttpClient(OkHttp) {
    engine {
        config {
            retryOnConnectionFailure(true)
            connectTimeout(defaultConnectionTimeout.toJavaDuration())
        }
    }
}
