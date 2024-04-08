package com.example.news.feature.article.list

import androidx.compose.runtime.Immutable

@Immutable
internal interface ArticleListScreenActions : ArticleListActions, ArticleSearchBarActions

@Immutable
internal interface ArticleListActions {
    fun onArticleClick(articleId: String)

    fun onReloadClick()
}

@Immutable
internal interface ArticleSearchBarActions {
    fun onQueryChange(updatedQuery: String)

    fun onClearQueryClick()
}
