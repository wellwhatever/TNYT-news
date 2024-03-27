package com.example.news.feature.article.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.news.feature.article.R
import com.example.news.feature.article.destinations.ArticleDetailScreenDestination
import com.example.news.feature.article.ui.GenericScreenLoading
import com.example.news.shared.code.model.Article
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.collections.immutable.ImmutableList
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun ArticleListScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: ArticleListViewModel = koinViewModel(),
) {
    val state = viewModel.articleListScreenState.collectAsStateWithLifecycle()

    CollectNavigationEvents(
        navigator = navigator,
        navigationActions = viewModel as ArticleListNavigation,
    )

    ArticleListScreenInternal(
        state = state.value,
        modifier = modifier.fillMaxSize(),
        actions = viewModel as ArticleListScreenActions,
    )
}

@Composable
private fun CollectNavigationEvents(
    navigator: DestinationsNavigator,
    navigationActions: ArticleListNavigation,
) {
    navigationActions.navigateToArticleDetail.CollectNavigationEvent {
        navigator.navigate(
            ArticleDetailScreenDestination(
                detailId = it,
            ),
        )
    }

    navigationActions.navigateBack.CollectNavigationEvent {
        navigator.popBackStack()
    }
}

@Composable
private fun ArticleListScreenInternal(
    state: ArticleListScreenState,
    actions: ArticleListScreenActions,
    modifier: Modifier = Modifier,
) {
    when (state) {
        ArticleListScreenState.Loading -> GenericScreenLoading(modifier = modifier)

        is ArticleListScreenState.Content -> {
            ArticleSearchBar(
                modifier = modifier,
                query = state.searchBarState.searchQuery,
                actions = actions,
            ) {
                ArticleListContent(
                    state = state.articleListState,
                    actions = actions,
                )
            }
        }
    }
}

@Composable
private fun ArticleListContent(
    state: ArticleListState,
    actions: ArticleListActions,
    modifier: Modifier = Modifier,
) {
    when (state) {
        ArticleListState.Loading -> GenericScreenLoading(modifier = modifier)
        is ArticleListState.Error -> {
            // TODO implement error handling!
        }

        is ArticleListState.Content -> ArticleListContent(
            modifier = modifier,
            articles = state.articles,
            onArticleClick = actions::onArticleClick,
        )
    }
}

@Composable
private fun ArticleSearchBar(
    query: String,
    actions: ArticleSearchBarActions,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    Column(
        modifier = modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = query,
            onValueChange = actions::onQueryChange,
            placeholder = @Composable {
                Text(
                    text = stringResource(id = R.string.article_search_field_placeholder),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline,
                    maxLines = 1,
                )
            },
            trailingIcon = @Composable {
                Icon(
                    modifier = Modifier.clickable(
                        onClick = actions::onClearQueryClick,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false),
                    ),
                    painter = painterResource(id = R.drawable.ic_close_24),
                    contentDescription = stringResource(R.string.article_search_clear_query),
                )
            },
            shape = CircleShape,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
        )
        content()
    }
}

@Composable
private fun ArticleListContent(
    articles: ImmutableList<Article>,
    modifier: Modifier = Modifier,
    onArticleClick: (String) -> Unit,
) {
    if (articles.isNotEmpty()) {
        ArticleList(
            modifier = modifier,
            articles = articles,
            onArticleClick = onArticleClick,
        )
    } else {
        ArticleListEmpty(modifier = modifier)
    }
}

@Composable
private fun ArticleList(
    articles: ImmutableList<Article>,
    modifier: Modifier = Modifier,
    onArticleClick: (String) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(
            items = articles,
            key = { _, item -> item.id },
        ) { index, article ->
            ArticleListItem(
                article = article,
                onArticleClick = onArticleClick,
            )
            if (index != articles.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun ArticleListItem(
    article: Article,
    modifier: Modifier = Modifier,
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
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            // TODO fix this
            Text(
                text = article.publishedDate.toString(),
                style = MaterialTheme.typography.titleSmall,
            )
        }
        Column(
            modifier = Modifier.weight(0.5f),
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                text = article.section,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
private fun ArticleListEmpty(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.article_search_no_results),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}
