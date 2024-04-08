package com.example.news.shared.data.remote

import com.example.news.shared.core.network.DomainException
import com.example.news.shared.core.network.DomainExceptionMapper
import com.example.news.shared.core.network.ServerException
import com.example.news.shared.core.network.TooManyRequests
import com.example.news.shared.core.network.UnexpectedException
import io.ktor.client.statement.HttpResponse

class RemoteDomainExceptionMapper : DomainExceptionMapper {
    override fun toDomainException(response: HttpResponse): DomainException =
        when (response.status.value) {
            in 500 until 600 -> ServerException
            429 -> TooManyRequests
            else -> UnexpectedException
        }
}
