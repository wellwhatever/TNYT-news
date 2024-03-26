package com.example.news.shared.data.di

import com.example.news.di.databaseModule
import com.example.news.di.platformDatabaseModule
import com.example.news.shared.code.data.ArticlesRepository
import com.example.news.shared.core.common.di.IoDispatcher
import com.example.news.shared.core.di.networkPlatformModule
import com.example.news.shared.core.network.NetworkClient
import com.example.news.shared.data.ArticleRepositoryImpl
import com.example.news.shared.data.local.ArticleEntityConverter
import com.example.news.shared.data.local.ArticleLocalDataSource
import com.example.news.shared.data.remote.ArticleRemoteDataSource
import com.example.news.shared.data.remote.ArticleResponseConverter
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val articleDataModule = module {
    includes(
        networkPlatformModule,
        platformDatabaseModule,
        databaseModule
    )

    single {
        NetworkClient(
            baseUrl = "https://api.nytimes.com/",
            httpClient = get(),
            // TODO extract it to secrets!
            apiKey = "Dt7Q08LpKCGVrKTxfWtpxAguSrM6ddTx",
        )
    }

    singleOf(::ArticleRemoteDataSource)
    single {
        ArticleLocalDataSource(
            get(),
            get(),
            get(named(IoDispatcher)),
        )
    }
    singleOf(::ArticleRepositoryImpl) bind ArticlesRepository::class

    factoryOf(::ArticleResponseConverter)
    factoryOf(::ArticleEntityConverter)
}
