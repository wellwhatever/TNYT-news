package com.example.news.shared.core.di

import com.example.news.shared.core.network.provideHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal actual val platformModule = module {
    singleOf(::provideHttpClient)
}
