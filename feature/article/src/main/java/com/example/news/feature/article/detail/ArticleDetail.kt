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
import com.example.news.feature.article.ui.GenericScreenLoading
import com.example.news.shared.code.model.Article
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Suppress("UnusedParameter")
@Destination
@Composable
fun ArticleDetailScreen(
    navigator: DestinationsNavigator,
    detailId: String,
    viewModel: ArticleDetailViewModel = koinViewModel()
) {
    val state = viewModel.screenState.collectAsStateWithLifecycle()
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.eventShareArticle.collect {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, it)
                type = "text/css"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.eventRedirectToWeb.collect {
            uriHandler.openUri(it)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.eventNavigateBack.collect {
            navigator.popBackStack()
        }
    }

    ArticleDetailScreenInternal(
        state = state.value,
        actions = viewModel as ArticleDetailScreenActions,
    )
}

@Composable
fun ArticleDetailScreenInternal(
    state: ArticleDetailScreenState,
    modifier: Modifier = Modifier,
    actions: ArticleDetailScreenActions
) {
    when (state) {
        is ArticleDetailScreenState.Content -> ArticleDetailScreenContent(
            article = state.article,
            modifier = modifier,
            onRedirectToWebClick = actions::onRedirectToWebClick,
            onShareClick = actions::onShareArticleClick
        )

        is ArticleDetailScreenState.Error -> {
            // TODO add error screen
        }

        ArticleDetailScreenState.Loading -> GenericScreenLoading(modifier = modifier)
    }
}

@Composable
fun ArticleDetailScreenContent(
    article: Article,
    modifier: Modifier = Modifier,
    onRedirectToWebClick: (String) -> Unit,
    onShareClick: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CoilLoadingImage(
            imageUrl = article.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 16.dp)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
        )
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ArticleDetailScreenActionHeader(
                section = article.section,
                webUrl = article.webUrl,
                onRedirectToWebClick = onRedirectToWebClick,
                onShareClick = onShareClick,
            )
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Text(
                text = article.firstParagraph,
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = article.publishedDate.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun ArticleDetailScreenActionHeader(
    section: String,
    webUrl: String,
    onRedirectToWebClick: (String) -> Unit,
    onShareClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = section,
            style = MaterialTheme.typography.titleMedium
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
