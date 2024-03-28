package com.example.news.database

import app.cash.sqldelight.db.SqlDriver
import com.example.news.ArticleDatabase

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): ArticleDatabase {
    val driver = driverFactory.createDriver()
    return ArticleDatabase(driver)
}
