package com.example.news.shared.data.di

import com.example.news.di.databaseModule
import com.example.news.di.platformDatabaseModule
import com.example.news.shared.core.common.di.IoDispatcher
import com.example.news.shared.core.data.ArticleRepository
import com.example.news.shared.core.di.networkPlatformModule
import com.example.news.shared.core.network.DomainExceptionMapper
import com.example.news.shared.core.network.NetworkClient
import com.example.news.shared.data.ArticleRepositoryImpl
import com.example.news.shared.data.local.ArticleEntityConverter
import com.example.news.shared.data.local.ArticleLocalDataSource
import com.example.news.shared.data.local.ArticleLocalDataSourceImpl
import com.example.news.shared.data.remote.ArticleRemoteDataSource
import com.example.news.shared.data.remote.ArticleRemoteDataSourceImpl
import com.example.news.shared.data.remote.ArticleResponseConverter
import com.example.news.shared.data.remote.RemoteDomainExceptionMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val articleDataModule = module {
    includes(
        networkPlatformModule,
        platformDatabaseModule,
        databaseModule,
    )

    single {
        NetworkClient(
            // This should be extracted to secrets
            apiKey = "Dt7Q08LpKCGVrKTxfWtpxAguSrM6ddTx",
            baseUrl = "https://api.nytimes.com/",
            httpClient = get(),
            domainExceptionMapper = get(),
        )
    }

    singleOf(::ArticleRemoteDataSourceImpl) bind ArticleRemoteDataSource::class
    single {
        ArticleLocalDataSourceImpl(
            get(),
            get(),
            get(named(IoDispatcher)),
        )
    } bind ArticleLocalDataSource::class
    singleOf(::ArticleRepositoryImpl) bind ArticleRepository::class

    factory {
        ArticleResponseConverter("https://www.nytimes.com/")
    }
    factoryOf(::ArticleEntityConverter)
    factoryOf(::RemoteDomainExceptionMapper) bind DomainExceptionMapper::class
}
