package com.example.news.feature.article.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.feature.article.destinations.ArticleDetailScreenDestination
import com.example.news.shared.core.common.stateInVmWithInitial
import com.example.news.shared.domain.articles.GetArticleUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ArticleDetailViewModel(
    getArticle: GetArticleUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), ArticleDetailScreenActions {
    private val errorFlow = MutableStateFlow<String?>(null)
    private val articleFlow = getArticle(
        ArticleDetailScreenDestination.argsFrom(savedStateHandle).detailId,
    )

    private val _eventFlow = Channel<ArticleDetailEvent>(Channel.CONFLATED)
    val eventFlow = _eventFlow.receiveAsFlow()

    val screenState: StateFlow<ArticleDetailScreenState> =
        combine(articleFlow, errorFlow) { article, error ->
            when {
                error != null -> ArticleDetailScreenState.Error(error)
                article != null -> ArticleDetailScreenState.Content(article)
                else -> ArticleDetailScreenState.Loading
            }
        }.stateInVmWithInitial(ArticleDetailScreenState.Loading)

    override fun onBackClick() {
        viewModelScope.launch {
            _eventFlow.send(ArticleDetailEvent.NavigateBack)
        }
    }

    override fun onRedirectToWebClick(url: String) {
        viewModelScope.launch {
            _eventFlow.send(ArticleDetailEvent.RedirectToWeb(url))
        }
    }

    override fun onShareArticleClick(url: String) {
        viewModelScope.launch {
            _eventFlow.send(ArticleDetailEvent.ShareArticle(url))
        }
    }
}
