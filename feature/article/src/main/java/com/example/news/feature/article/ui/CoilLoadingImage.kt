package com.example.news.feature.article.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size

@Composable
fun CoilLoadingImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    alignment: Alignment = Alignment.Center,
    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
    error: Painter? = null,
    colorFilter: ColorFilter? = null,
    onSuccess: (String) -> Unit = {},
) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data("https://www.nytimes.com/$imageUrl")
        .crossfade(true)
        .size(Size.ORIGINAL)
        .scale(Scale.FIT)
        .build()

    AsyncImage(
        model = imageRequest,
        contentScale = ContentScale.FillWidth,
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        onError = onError,
        colorFilter = colorFilter,
        error = error,
        onSuccess = { onSuccess(imageUrl) },
    )
}