package com.example.tradernet_test_task_simplified.presenter.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun LoadableImage(ticker: String?) {
    val imageSize = 24.dp.dpToPx().toInt()

    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://tradernet.com/logos/get-logo-by-ticker?ticker=${ticker?.lowercase()}")
            .size(Size(imageSize, imageSize))
            .crossfade(true)
            .build()
    )

    AnimatedVisibility(visible = imagePainter.state is AsyncImagePainter.State.Success) {
        Image(
            painter = imagePainter,
            contentDescription = ticker,
            modifier = Modifier
                .wrapContentSize(),
            contentScale = ContentScale.Fit
        )
    }
}