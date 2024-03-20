package com.example.news.shared.core.di

import com.example.news.shared.core.network.provideHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val networkPlatformModule = module {
    singleOf(::provideHttpClient)
}
