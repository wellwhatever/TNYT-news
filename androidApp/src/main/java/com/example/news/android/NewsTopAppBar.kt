package com.example.news.android

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow

@ExperimentalMaterial3Api
@Composable
internal fun NewsTopAppBar(
    appState: NewsAppState,
    navigateBack: () -> Unit,
    onMoreClick: () -> Unit,
    colors: TopAppBarColors = NewsTopAppBarColors(),
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(id = appState.navigationTitleRes),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        },
        navigationIcon = {
            if (appState.showNavigationIcon) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onMoreClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_more_vert_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
        colors = colors,
    )
}

@ExperimentalMaterial3Api
@Composable
private fun NewsTopAppBarColors(): TopAppBarColors =
    TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
    )
