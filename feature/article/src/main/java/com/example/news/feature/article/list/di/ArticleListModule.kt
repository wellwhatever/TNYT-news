package com.example.news.feature.article.list.di

import com.example.news.feature.article.list.ArticleListErrorMapper
import com.example.news.feature.article.list.ArticleListNavigationImpl
import com.example.news.feature.article.list.ArticleListViewModel
import com.example.news.shared.domain.di.articleDomainModule
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val articleListModule = module {
    includes(articleDomainModule)

    viewModelOf(::ArticleListViewModel)

    factoryOf(::ArticleListNavigationImpl)
    factoryOf(::ArticleListErrorMapper)
}
