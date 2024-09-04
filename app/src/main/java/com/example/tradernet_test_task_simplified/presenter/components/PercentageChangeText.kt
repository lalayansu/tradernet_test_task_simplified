package com.example.tradernet_test_task_simplified.presenter.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tradernet_test_task_simplified.data.model.response.AnimationDirection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal

@Composable
fun PercentageChangeText(
    modifier: Modifier = Modifier,
    percentageChangeValue: BigDecimal? = null,
    percentageChangeText: String? = null,
    shouldAnimate: Boolean? = false,
    onAnimationEnd: () -> Unit = {},
    animationDirection: AnimationDirection? = AnimationDirection.NONE,
    defaultTextColor: Color = MaterialTheme.colorScheme.onBackground,
    defaultBackgroundColor: Color = Color.Transparent,
    positivePercentageChangeBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    positivePercentageChangeTextColor: Color = MaterialTheme.colorScheme.background,
    positivePercentageTextColor: Color = MaterialTheme.colorScheme.primary,
    negativePercentageChangeBackgroundColor: Color = MaterialTheme.colorScheme.error,
    negativePercentageChangeTextColor: Color = MaterialTheme.colorScheme.background,
    negativePercentageTextColor: Color = MaterialTheme.colorScheme.error,
) {
    val backgroundColorAnimation = remember { Animatable(defaultBackgroundColor) }
    val targetTextColorAnimation = remember {
        Animatable(
            initialValue = percentageChangeValue?.let {
                if (percentageChangeValue > BigDecimal.ZERO) positivePercentageTextColor
                else negativePercentageTextColor
            } ?: defaultTextColor
        )
    }

    val coroutineScope = rememberCoroutineScope { Dispatchers.Main }

    LaunchedEffect(percentageChangeValue, shouldAnimate) {
        if (shouldAnimate == true) {
            percentageChangeValue?.let { percent ->
                coroutineScope.launch {
                    when (animationDirection) {
                        AnimationDirection.UP -> {
                            backgroundColorAnimation.snapTo(positivePercentageChangeBackgroundColor)
                            targetTextColorAnimation.snapTo(positivePercentageChangeTextColor)

                            coroutineScope.launch {
                                backgroundColorAnimation.animateTo(
                                    targetValue = defaultBackgroundColor,
                                    animationSpec = tween(durationMillis = 500)
                                )
                            }

                            targetTextColorAnimation.animateTo(
                                targetValue = if (percent > BigDecimal.ZERO) positivePercentageTextColor
                                else negativePercentageTextColor,
                                animationSpec = tween(durationMillis = 500)
                            )
                        }

                        AnimationDirection.DOWN -> {
                            backgroundColorAnimation.snapTo(negativePercentageChangeBackgroundColor)
                            targetTextColorAnimation.snapTo(negativePercentageChangeTextColor)

                            coroutineScope.launch {
                                backgroundColorAnimation.animateTo(
                                    targetValue = defaultBackgroundColor,
                                    animationSpec = tween(durationMillis = 500)
                                )
                            }

                            targetTextColorAnimation.animateTo(
                                targetValue = if (percent > BigDecimal.ZERO) positivePercentageTextColor
                                else negativePercentageTextColor,
                                animationSpec = tween(durationMillis = 500)
                            )
                        }

                        else -> targetTextColorAnimation.snapTo(
                            if (percentageChangeValue > BigDecimal.ZERO) positivePercentageTextColor
                            else negativePercentageTextColor
                        )
                    }
                }
            }
            onAnimationEnd.invoke()
        }
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColorAnimation.value)
    ) {
        Text(
            text = percentageChangeText.orEmpty(),
            color = targetTextColorAnimation.value,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Preview
@Composable
fun PercentageChangeTextPreview() {
    Column {
        Row {
            PercentageChangeText(
                percentageChangeValue = 1.45.toBigDecimal(),
                shouldAnimate = true,
                percentageChangeText = "+1.45%"
            )

            Spacer(modifier = Modifier.size(16.dp))

            PercentageChangeText(
                percentageChangeValue = (-1.45).toBigDecimal(),
                shouldAnimate = true,
                percentageChangeText = "-1.45%"
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        Row {
            PercentageChangeText(
                percentageChangeValue = 1.45.toBigDecimal(),
                shouldAnimate = false,
                percentageChangeText = "+1.45%"
            )

            Spacer(modifier = Modifier.size(16.dp))

            PercentageChangeText(
                percentageChangeValue = (-1.45).toBigDecimal(),
                shouldAnimate = false,
                percentageChangeText = "-1.45%",
            )
        }
    }
}