package com.example.news.feature.article.list

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.news.feature.article.ui.NavigationDelegate

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
    fun onBackClick()

    fun onClearQueryClick()
}

@Immutable
internal interface ArticleListNavigation {
    val navigateToArticleDetail: NavigationDelegate<String>
    val navigateBack: NavigationDelegate<Unit>
}

@Stable
internal class ArticleListNavigationImpl : ArticleListNavigation {
    override val navigateToArticleDetail: NavigationDelegate<String> = NavigationDelegate()
    override val navigateBack: NavigationDelegate<Unit> = NavigationDelegate()
}
