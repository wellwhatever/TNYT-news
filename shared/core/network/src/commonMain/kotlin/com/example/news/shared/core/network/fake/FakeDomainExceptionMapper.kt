package com.example.news.shared.core.network.fake

import com.example.news.shared.core.network.DomainException
import com.example.news.shared.core.network.DomainExceptionMapper
import com.example.news.shared.core.network.UnexpectedException
import io.ktor.client.statement.HttpResponse

class FakeDomainExceptionMapper : DomainExceptionMapper {
    override fun toDomainException(response: HttpResponse): DomainException =
        UnexpectedException
}
