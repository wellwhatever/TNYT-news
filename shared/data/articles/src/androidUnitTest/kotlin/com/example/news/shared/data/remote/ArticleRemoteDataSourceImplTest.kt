package com.example.news.shared.data.remote

import com.example.news.shared.core.model.Article
import com.example.news.shared.core.network.NetworkClient
import com.example.news.shared.core.network.fake.FakeDomainExceptionMapper
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.datetime.LocalDate

class ArticleRemoteDataSourceImplTest : FunSpec({
    lateinit var articleRemoteDataSource: ArticleRemoteDataSourceImpl

    beforeEach {
        articleRemoteDataSource = ArticleRemoteDataSourceImpl(
            networkClient = NetworkClient(
                baseUrl = "https://testurl.com",
                apiKey = "testApiKey",
                domainExceptionMapper = FakeDomainExceptionMapper(),
                httpClient = HttpClient(MockEngine { request ->
                    val content = ByteReadChannel(
                        when (request.url.parameters["query"]) {
                            "Lorem", "" -> {
                                articlesResponse
                            }

                            else -> """{}"""
                        }
                    )
                    respond(
                        content = content, status = HttpStatusCode.OK, headers = headersOf(
                            HttpHeaders.ContentType, "application/json"
                        )
                    )
                })
            ),
            articleConverter = ArticleResponseConverter("")
        )
    }

    test("getArticles must return valid articles") {
        val query = ""
        val result = articleRemoteDataSource.getArticles(query)
        val expected = parsedArticlesResponse

        result shouldBe expected
    }

    test("getArticles must compose valid request with given parameters") {
        val query = "Lorem"
        val result = articleRemoteDataSource.getArticles(query)
        val expected = parsedArticlesResponse

        result shouldBe expected
    }
})

const val articlesResponse = """
{
  "copyright": "Copyright 2024",
  "response": {
    "docs": [
      {
        "abstract": "Sample abstract",
        "byline": {
          "organization": null,
          "original": "John Doe",
          "person": []
        },
        "section_name": "Sample Section"
        "source": "New York Times"
        "document_type": "article",
        "_id": "123456789",
        "keywords": [
          {
            "major": "",
            "name": "Keyword",
            "rank": 0,
            "value": "keyword1"
          },
          {
            "major": "",
            "name": "Keyword",
            "rank": 0,
            "value": "keyword2"
          }
        ],
        "lead_paragraph": "Sample lead paragraph",
        "multimedia": [
          {
            "crop_name": "",
            "height": 0,
            "rank": 0,
            "subType": "",
            "subtype": "",
            "type": "image",
            "url": "https://example.com/image1.jpg",
            "width": 0
          },
          {
            "crop_name": "",
            "height": 0,
            "rank": 0,
            "subType": "",
            "subtype": "",
            "type": "image",
            "url": "https://example.com/image2.jpg",
            "width": 0
          }
        ],
        "pub_date": "2024-04-10T00:00:00+0000",
        "snippet": "Sample snippet",
        "uri": "nyt://article/123456789",
        "web_url": "https://example.com/article",
        "word_count": 500
      }
    ]
  },
  "status": "OK"
}
"""

val parsedArticlesResponse = listOf(
    Article(
        id = "123456789",
        title = "Sample abstract",
        firstParagraph = "Sample lead paragraph",
        publishedDate = LocalDate.parse("2024-04-10"),
        section = "Sample Section",
        source = "New York Times",
        imageUrl = "https://example.com/image1.jpg",
        webUrl = "https://example.com/article",
        desk = ""
    )
)