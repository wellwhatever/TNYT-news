package com.example.news.feature.article.detail.di

import com.example.news.feature.article.detail.ArticleDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val articleDetailModule = module {
    viewModelOf(::ArticleDetailViewModel)
}
