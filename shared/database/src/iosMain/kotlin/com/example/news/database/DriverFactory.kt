package com.example.news.database

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.inMemoryDriver
import com.example.news.ArticleDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return inMemoryDriver(
            ArticleDatabase.Schema.synchronous(),
        )
    }
}
