package com.example.news

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform