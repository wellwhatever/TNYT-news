package com.example.news.feature.article.detail

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.news.feature.article.R
import com.example.news.feature.article.ui.CoilLoadingImage
import com.example.news.feature.article.ui.GenericErrorScreen
import com.example.news.feature.article.ui.GenericScreenLoading
import com.example.news.shared.core.model.Article
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Suppress("UnusedParameter")
@Destination
@Composable
fun ArticleDetailScreen(
    navigator: DestinationsNavigator,
    detailId: String,
    viewModel: ArticleDetailViewModel = koinViewModel(),
) {
    val state = viewModel.screenState.collectAsStateWithLifecycle()
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    CollectScreenEvents(
        eventFlow = viewModel.eventFlow,
        navigateBack = navigator::popBackStack,
        onRedirectToWeb = uriHandler::openUri,
        onShareArticle = {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, it)
                type = "text/css"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        },
    )

    ArticleDetailScreenInternal(
        state = state.value,
        actions = viewModel as ArticleDetailScreenActions,
    )
}

@Composable
private fun CollectScreenEvents(
    eventFlow: Flow<ArticleDetailEvent>,
    navigateBack: () -> Unit,
    onRedirectToWeb: (String) -> Unit,
    onShareArticle: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        eventFlow.collectLatest {
            when (it) {
                is ArticleDetailEvent.RedirectToWeb -> onRedirectToWeb(it.url)
                is ArticleDetailEvent.ShareArticle -> onShareArticle(it.articleUrl)
                ArticleDetailEvent.NavigateBack -> navigateBack()
            }
        }
    }
}

@Composable
private fun ArticleDetailScreenInternal(
    state: ArticleDetailScreenState,
    actions: ArticleDetailScreenActions,
    modifier: Modifier = Modifier,
) {
    when (state) {
        ArticleDetailScreenState.Loading -> GenericScreenLoading(modifier = modifier)
        is ArticleDetailScreenState.Error -> GenericErrorScreen()

        is ArticleDetailScreenState.Content -> ArticleDetailScreenContent(
            article = state.article,
            modifier = modifier,
            onRedirectToWebClick = actions::onRedirectToWebClick,
            onShareClick = actions::onShareArticleClick,
        )
    }
}

@Composable
private fun ArticleDetailScreenContent(
    article: Article,
    onRedirectToWebClick: (String) -> Unit,
    onShareClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CoilLoadingImage(
            imageUrl = article.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 16.dp)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
        )
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ArticleDetailHeadline(
                section = article.section,
                webUrl = article.webUrl,
                onRedirectToWebClick = onRedirectToWebClick,
                onShareClick = onShareClick,
            )
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(
                modifier = Modifier.height(8.dp),
            )
            Text(
                text = article.firstParagraph,
                style = MaterialTheme.typography.bodyMedium,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Text(
                    text = article.publishedDate.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
private fun ArticleDetailHeadline(
    section: String,
    webUrl: String,
    onRedirectToWebClick: (String) -> Unit,
    onShareClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = section,
            style = MaterialTheme.typography.titleMedium,
        )
        Row(
            horizontalArrangement = Arrangement.End,
        ) {
            IconButton(onClick = { onRedirectToWebClick(webUrl) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_open_in_web_24),
                    contentDescription = null,
                )
            }
            IconButton(onClick = { onShareClick(webUrl) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_share_24),
                    contentDescription = null,
                )
            }
        }
    }
}
