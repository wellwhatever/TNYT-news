package com.example.news.feature.article.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment

@Composable
fun GenericScreenLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
        )
    }
}
