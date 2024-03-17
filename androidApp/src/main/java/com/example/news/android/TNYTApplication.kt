package com.example.news.android

import android.app.Application
import com.example.news.feature.article.list.di.articleListModule
import com.example.news.shared.domain.di.articleDomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class TNYTApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeTimber()
        startKoin {
            androidLogger()
            androidContext(this@TNYTApplication)
            modules(articleDomainModule, articleListModule)
        }
    }
}

private fun initializeTimber() {
    if (BuildConfig.DEBUG) {
        Timber.plant(Timber.DebugTree())
    }
}