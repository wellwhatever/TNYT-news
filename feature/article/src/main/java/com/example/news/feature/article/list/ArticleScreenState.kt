package com.example.news.feature.article.list

import com.example.news.shared.code.model.Article
import kotlinx.collections.immutable.ImmutableList

sealed interface ArticleListState {
    data object Loading : ArticleListState

    data class Error(val message: String) : ArticleListState

    data class Content(
        val articles: ImmutableList<Article>,
    ) : ArticleListState
}

data class ArticleSearchBarState(
    val searchQuery: String,
)

sealed interface ArticleListScreenState {
    data object Loading : ArticleListScreenState

    data class Content(
        val articleListState: ArticleListState,
        val searchBarState: ArticleSearchBarState,
    ) : ArticleListScreenState
}
