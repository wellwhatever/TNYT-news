package com.example.news.shared.core.network

sealed class ApiException : RuntimeException()
class UnexpectedException : ApiException()
class NoInternetException : ApiException()

class HttpExceptionDomain(val code: Int) : ApiException()

expect fun Throwable.isNoInternet(): Boolean
