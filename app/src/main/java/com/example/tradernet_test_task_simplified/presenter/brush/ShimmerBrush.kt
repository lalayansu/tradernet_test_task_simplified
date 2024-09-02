package com.example.presenter.components.brush

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun shimmerBrush(showShimmer: Boolean = true) = if (showShimmer) {
    val shimmerColors = listOf(
        MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
        MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
        MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "shimer")

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(500), repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, y = translateAnimation.value)
    )
} else {
    Brush.linearGradient(
        colors = listOf(Color.Transparent, Color.Transparent),
        start = Offset.Zero,
        end = Offset.Zero
    )
}