package com.example.news.feature.article.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.shared.code.model.Article
import com.example.news.shared.domain.articles.GetMostViewedArticlesUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class ArticleListViewModel(
    private val getMostViewedArticles: GetMostViewedArticlesUseCase
) : ViewModel(), ArticleListActions {
    private val errorFlow = MutableStateFlow<String?>(null)
    private val articlesFlow = MutableStateFlow<List<Article>?>(null)

    private val _navigateToDetailScreen = Channel<String>(Channel.CONFLATED)
    val navigateToDetailScreen = _navigateToDetailScreen.receiveAsFlow()

    val articleListState: StateFlow<ArticleListScreenState> = combine(
        articlesFlow,
        errorFlow,
    ) { articles, error ->
        when {
            error != null -> ArticleListScreenState.Error(error)
            articles == null -> ArticleListScreenState.Loading
            else -> ArticleListScreenState.Content(articles)
        }
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ArticleListScreenState.Loading
    )

    init {
        fetchArticles()
    }

    private fun fetchArticles() {
        viewModelScope.launch {
            try {
                articlesFlow.value = getMostViewedArticles()
            } catch (exc: Exception) {
                // TODO handle exception properly
                Timber.e(exc)
            }
        }
    }

    override fun onArticleClick(articleId: String) {
        viewModelScope.launch {
            _navigateToDetailScreen.send(articleId)
        }
    }
}

sealed interface ArticleListScreenState {
    data object Loading : ArticleListScreenState

    data class Error(val message: String) : ArticleListScreenState

    data class Content(
        val articles: List<Article>
    ) : ArticleListScreenState
}