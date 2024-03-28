package com.example.news.shared.core.network

sealed class DomainException : RuntimeException()
data object UnexpectedException : DomainException()
data object NoInternetException : DomainException()
data object TooManyRequests : DomainException()
data object ServerException : DomainException()

expect fun Throwable.isNoInternet(): Boolean
