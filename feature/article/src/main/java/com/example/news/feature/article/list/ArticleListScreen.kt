package com.example.news.feature.article.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.news.feature.article.destinations.ArticleDetailScreenDestination
import com.example.news.shared.code.model.Article
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun ArticleListScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: ArticleListViewModel = koinViewModel(),
) {
    val state = viewModel.articleListState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigateToDetailScreen.collect {
            navigator.navigate(ArticleDetailScreenDestination(detailId = it))
        }
    }

    ArticleListInternal(
        state = state.value,
        modifier = modifier.fillMaxSize(),
        actions = viewModel
    )
}

@Composable
fun ArticleListInternal(
    modifier: Modifier = Modifier,
    state: ArticleListScreenState,
    actions: ArticleListActions,
) {
    when (state) {
        ArticleListScreenState.Loading -> FullScreenLoading(modifier = modifier)
        is ArticleListScreenState.Error -> {
            // TODO implement error handling!
        }

        is ArticleListScreenState.Content -> ArticleList(
            modifier = modifier,
            articles = state.articles,
            actions = actions
        )
    }
}

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    actions: ArticleListActions,
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(
            items = articles,
            key = { _, item -> item.id }
        ) { index, article ->
            ArticleListItem(
                article = article,
                onArticleClick = actions::onArticleClick
            )
            if (index != articles.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun ArticleListItem(
    modifier: Modifier = Modifier,
    article: Article,
    onArticleClick: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onArticleClick(article.id)
            }
            .padding(8.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = article.publishedDate.toString(),
                style = MaterialTheme.typography.titleSmall
            )
        }
        Column(
            modifier = Modifier.weight(0.5f),
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                text = article.section,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


@Composable
fun FullScreenLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
        )
    }
}