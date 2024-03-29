package com.example.news.shared.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

internal fun provideHttpClient() = HttpClient(Darwin) {
    engine {
        configureRequest {
            setAllowsCellularAccess(true)
        }
    }
}
