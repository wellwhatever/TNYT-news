package com.example.news.feature.article.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.shared.core.common.stateInVmWithInitial
import com.example.news.shared.core.model.Article
import com.example.news.shared.core.network.DomainException
import com.example.news.shared.domain.articles.GetArticlesUseCase
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class ArticleListViewModel internal constructor(
    private val getMostViewedArticles: GetArticlesUseCase,
    private val errorMapper: ArticleListErrorMapper,
) : ViewModel(),
    ArticleListScreenActions {
    private val errorFlow = MutableStateFlow<DomainException?>(null)
    private val searchQueryFlow = MutableStateFlow("")

    private val _eventFlow = Channel<ArticleListEvent>(Channel.CONFLATED)
    val eventFlow = _eventFlow.receiveAsFlow()

    private val filteredArticles: Flow<List<Article>?> = searchQueryFlow.onEach {
        println("filteredArticles: $it")
    }
        .transformLatest {
            emit(null)
            emit(getFilteredArticles(it))
        }

    val searchBarState = searchQueryFlow.onEach {
        println("searchBarState, searchQueryFlow: $it")
    }.map {
        ArticleSearchBarState(it)
    }

    val articleListState = combine(
        filteredArticles.onEach {
            println("articleListState, filteredArticles: $it")
        },
        errorFlow.onEach {
            println("articleListState, errorFlow: $it")
        },
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
        searchBarState.onEach {
            println("articleListScreenState, searchbarstate: $it")
        },
        articleListState.onEach {
            println("articleListScreenState, articleListState: $it")
        },
    ) { searchState, articleState ->
        println("combined:" + "search:$searchState" + "articles:$articleState")
        ArticleListScreenState.Content(
            articleListState = articleState,
            searchBarState = searchState,
        )
    }.stateInVmWithInitial(ArticleListScreenState.Loading)

    val screenStateFlow: Flow<ArticleListScreenState> = articleListScreenState

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
