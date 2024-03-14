package com.example.news.shared.core.network

import java.net.UnknownHostException

internal actual fun Throwable.isNoInternet(): Boolean = this is UnknownHostException
