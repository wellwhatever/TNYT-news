package com.example.news.shared.data.di

import com.example.news.shared.code.data.ArticlesRepository.Companion.NEW_YORK_TIMES_BASE_URL
import com.example.news.shared.core.network.NetworkClient
import com.example.news.shared.data.OnlineFirstArticlesRepository
import com.example.news.shared.data.remote.ArticlesRemoteDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val dataModule = module {
    single {
        NetworkClient(
            baseUrl = NEW_YORK_TIMES_BASE_URL,
            httpClient = get(),
        )
    }

    singleOf(::ArticlesRemoteDataSource)
    singleOf(::OnlineFirstArticlesRepository)
}