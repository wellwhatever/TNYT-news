package com.example.news.di

import com.example.news.database.DriverFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformDatabaseModule = module {
    singleOf(::DriverFactory)
}