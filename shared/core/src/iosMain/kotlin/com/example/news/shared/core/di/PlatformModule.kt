package com.example.news.shared.core.di

import com.example.news.shared.core.network.provideHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule = module {
    single(named("default")) { provideHttpClient() }
}
