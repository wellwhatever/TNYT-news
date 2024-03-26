package com.example.news.feature.article.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.feature.article.destinations.ArticleDetailScreenDestination
import com.example.news.shared.code.model.Article
import com.example.news.shared.domain.articles.GetArticleUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ArticleDetailViewModel(
    getArticle: GetArticleUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ArticleDetailScreenActions {
    private val errorFlow = MutableStateFlow<String?>(null)
    private val articleFlow = getArticle(
        ArticleDetailScreenDestination.argsFrom(savedStateHandle).detailId
    )

    private val _eventRedirectToWeb = Channel<String>(Channel.CONFLATED)
    val eventRedirectToWeb = _eventRedirectToWeb.receiveAsFlow()

    private val _eventShareArticle = Channel<String>(Channel.CONFLATED)
    val eventShareArticle = _eventShareArticle.receiveAsFlow()

    private val _eventNavigateBack = Channel<Unit>(Channel.CONFLATED)
    val eventNavigateBack = _eventNavigateBack.receiveAsFlow()

    val screenState: StateFlow<ArticleDetailScreenState> =
        combine(articleFlow, errorFlow) { article, error ->
            when {
                error != null -> ArticleDetailScreenState.Error(error)
                article != null -> ArticleDetailScreenState.Content(article)
                else -> ArticleDetailScreenState.Loading
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ArticleDetailScreenState.Loading
        )

    override fun onBackClick() {
        viewModelScope.launch {
            _eventNavigateBack.send(Unit)
        }
    }

    override fun onRedirectToWebClick(url: String) {
        viewModelScope.launch {
            _eventRedirectToWeb.send(url)
        }
    }

    override fun onShareArticleClick(url: String) {
        viewModelScope.launch {
            _eventShareArticle.send(url)
        }
    }
}

sealed interface ArticleDetailScreenState {
    data object Loading : ArticleDetailScreenState
    data class Error(val message: String) : ArticleDetailScreenState

    data class Content(
        val article: Article
    ) : ArticleDetailScreenState
}