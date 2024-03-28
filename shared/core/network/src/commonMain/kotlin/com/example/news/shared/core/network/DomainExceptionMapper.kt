package com.example.news.shared.core.network

import io.ktor.client.statement.HttpResponse

interface DomainExceptionMapper {
    fun toDomainException(response: HttpResponse): DomainException
}
