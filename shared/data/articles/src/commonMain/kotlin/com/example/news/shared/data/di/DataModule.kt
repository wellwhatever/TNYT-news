package com.example.news.shared.data.di

import com.example.news.shared.code.data.ArticlesRepository
import com.example.news.shared.code.data.ArticlesRepository.Companion.NEW_YORK_TIMES_BASE_URL
import com.example.news.shared.core.di.networkPlatformModule
import com.example.news.shared.core.network.NetworkClient
import com.example.news.shared.data.OnlineFirstArticlesRepository
import com.example.news.shared.data.converter.ArticleResponseConverter
import com.example.news.shared.data.remote.ArticlesRemoteDataSource
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val articleDataModule = module {
    includes(networkPlatformModule)

    single {
        NetworkClient(
            baseUrl = NEW_YORK_TIMES_BASE_URL,
            httpClient = get(),
            apiKey = "Dt7Q08LpKCGVrKTxfWtpxAguSrM6ddTx"
        )
    }

    singleOf(::ArticlesRemoteDataSource)
    singleOf(::OnlineFirstArticlesRepository) bind ArticlesRepository::class

    factoryOf(::ArticleResponseConverter)
}