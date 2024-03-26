package com.example.news.shared.domain.di

import com.example.news.shared.core.common.di.coreModule
import com.example.news.shared.data.di.articleDataModule
import com.example.news.shared.domain.articles.GetArticleUseCase
import com.example.news.shared.domain.articles.GetArticlesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val articleDomainModule = module {
    includes(articleDataModule)

    factoryOf(::GetArticlesUseCase)
    factoryOf(::GetArticleUseCase)
}
