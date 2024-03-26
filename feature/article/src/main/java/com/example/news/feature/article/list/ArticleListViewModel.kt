package com.example.news.feature.article.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.shared.code.model.Article
import com.example.news.feature.article.ui.NavigatorScope
import com.example.news.shared.domain.articles.GetArticlesUseCase
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class ArticleListViewModel internal constructor(
    private val getMostViewedArticles: GetArticlesUseCase,
    navigation: ArticleListNavigationImpl,
) : ViewModel(),
    NavigatorScope,
    ArticleListScreenActions,
    ArticleListNavigation by navigation {
    override val navigationScope = viewModelScope

    private val errorFlow = MutableStateFlow<String?>(null)
    private val searchQueryFlow = MutableStateFlow("")

    private val filteredArticles: Flow<List<Article>?> = searchQueryFlow
        .debounce(0.5.seconds)
        .transformLatest {
            emit(null)
            emit(getMostViewedArticles(it))
        }

    private val searchBarState = searchQueryFlow.map {
        ArticleSearchBarState(it)
    }

    private val articleListState = combine(
        filteredArticles,
        errorFlow,
    ) { articles, error ->
        when {
            error != null -> ArticleListState.Error(error)
            articles == null -> ArticleListState.Loading
            else -> ArticleListState.Content(
                articles = articles.toImmutableList(),
            )
        }
    }

    val articleListScreenState: StateFlow<ArticleListScreenState> = combine(
        searchBarState,
        articleListState,
    ) { searchState, articleState ->
        ArticleListScreenState.Content(
            articleListState = articleState,
            searchBarState = searchState,
        )
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ArticleListScreenState.Loading,
    )

    override fun onQueryChange(updatedQuery: String) {
        viewModelScope.launch {
            searchQueryFlow.value = updatedQuery
        }
    }

    override fun onClearQueryClick() {
        viewModelScope.launch {
            searchQueryFlow.value = ""
        }
    }

    override fun onArticleClick(articleId: String) {
        navigateToArticleDetail(articleId)
    }

    override fun onBackClick() {
        navigateBack(Unit)
    }
}
