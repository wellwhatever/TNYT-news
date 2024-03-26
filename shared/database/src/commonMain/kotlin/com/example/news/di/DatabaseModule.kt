package com.example.news.di

import com.example.news.database.createDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import com.example.news.ArticleDatabase
import com.example.news.shared.core.common.di.coreModule

expect val platformDatabaseModule: Module

val databaseModule = module {
    includes(coreModule)

    single {
        createDatabase(get())
    }
    single {
        get<ArticleDatabase>().articleDatabaseQueries
    }
}