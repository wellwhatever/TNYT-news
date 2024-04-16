package com.example.shared.core.testing.data

import com.example.news.shared.core.model.Article
import kotlinx.datetime.LocalDate

object FakeArticleDataProvider {
    val article1 = Article(
        id = "1",
        title = "Sample Title 1",
        firstParagraph = "Sample first paragraph 1",
        publishedDate = LocalDate(2024, 4, 8),
        section = "Sample Section 1",
        source = "Sample Source 1",
        imageUrl = "https://sample-url.com/image1.jpg",
        webUrl = "https://sample-url.com/article1",
        desk = "Sample Desk 1",
    )

    val article2 = Article(
        id = "2",
        title = "Sample Title 2",
        firstParagraph = "Sample first paragraph 2",
        publishedDate = LocalDate(2024, 4, 7),
        section = "Sample Section 2",
        source = "Sample Source 2",
        imageUrl = "https://sample-url.com/image2.jpg",
        webUrl = "https://sample-url.com/article2",
        desk = "Sample Desk 2",
    )

    val article3 = Article(
        id = "3",
        title = "Sample Title 3",
        firstParagraph = "Sample first paragraph 3",
        publishedDate = LocalDate(2024, 4, 6),
        section = "Sample Section 3",
        source = "Sample Source 3",
        imageUrl = "https://sample-url.com/image3.jpg",
        webUrl = "https://sample-url.com/article3",
        desk = "Sample Desk 3",
    )

    val fakeArticles = listOf(article1, article2, article3)
}
