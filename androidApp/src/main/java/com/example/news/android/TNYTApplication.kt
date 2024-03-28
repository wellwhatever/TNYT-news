package com.example.news.android

import android.app.Application
import com.example.news.android.di.appModule
import com.example.news.feature.article.detail.di.articleDetailModule
import com.example.news.feature.article.list.di.articleListModule
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
            modules(
                appModule,
                articleListModule,
                articleDetailModule,
            )
        }
    }
}

private fun initializeTimber() {
    if (BuildConfig.DEBUG) {
        Timber.plant(Timber.DebugTree())
    }
}
