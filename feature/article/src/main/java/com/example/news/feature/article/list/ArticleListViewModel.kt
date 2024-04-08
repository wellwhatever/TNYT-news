package com.example.news.feature.article.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.shared.code.model.Article
import com.example.news.shared.core.common.stateInVmWithInitial
import com.example.news.shared.core.network.DomainException
import com.example.news.shared.domain.articles.GetArticlesUseCase
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.time.Duration.Companion.seconds

class ArticleListViewModel internal constructor(
    private val getMostViewedArticles: GetArticlesUseCase,
    private val errorMapper: ArticleListErrorMapper,
) : ViewModel(),
    ArticleListScreenActions {
    private val errorFlow = MutableStateFlow<DomainException?>(null)
    private val searchQueryFlow = MutableStateFlow("")

    private val _eventFlow = Channel<ArticleListEvent>(Channel.CONFLATED)
    val eventFlow = _eventFlow.receiveAsFlow()

    private val filteredArticles: Flow<List<Article>?> = searchQueryFlow
        .debounce(0.5.seconds)
        .transformLatest {
            emit(null)
            emit(getFilteredArticles(it))
        }

    private val searchBarState = searchQueryFlow.map {
        ArticleSearchBarState(it)
    }

    private val articleListState = combine(
        filteredArticles,
        errorFlow,
    ) { articles, error ->
        when {
            error != null -> errorMapper.mapToArticleListError(error)
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
    }.stateInVmWithInitial(ArticleListScreenState.Loading)

    private suspend fun getFilteredArticles(query: String): List<Article>? {
        return try {
            errorFlow.value = null
            getMostViewedArticles(query)
        } catch (exception: DomainException) {
            Timber.e(exception)
            errorFlow.value = exception
            null
        }
    }

    override fun onQueryChange(updatedQuery: String) {
        searchQueryFlow.value = updatedQuery
    }

    override fun onClearQueryClick() {
        searchQueryFlow.value = ""
    }

    override fun onArticleClick(articleId: String) {
        viewModelScope.launch {
            _eventFlow.send(ArticleListEvent.NavigateToDetail(articleId))
        }
    }

    override fun onReloadClick() {
        errorFlow.value = null
        searchQueryFlow.value = ""
    }
}
