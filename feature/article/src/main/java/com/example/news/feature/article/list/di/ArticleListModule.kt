package com.example.news.feature.article.list.di

import com.example.news.feature.article.list.ArticleListNavigationImpl
import com.example.news.feature.article.list.ArticleListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val articleListModule = module {
    viewModelOf(::ArticleListViewModel)

    factoryOf(::ArticleListNavigationImpl)
}
