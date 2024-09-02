package com.example.tradernet_test_task_simplified.presenter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tradernet_test_task_simplified.data.model.response.AnimationDirection
import java.math.BigDecimal

@Composable
fun QuoteItemView(
    modifier: Modifier = Modifier,
    ticker: String? = null,
    exchangeOfLatestTrade: String? = null,
    name: String? = null,
    onAnimationEnd: () -> Unit = {},
    priceChangeByPercentage: BigDecimal? = null,
    percentageChangeText: String? = null,
    priceChangeInPointsText: String? = null,
    latestTradePriceText: String? = null,
    shouldAnimatePercentageChange: Boolean? = false,
    animationDirection: AnimationDirection? = AnimationDirection.NONE
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 12.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                LoadableImage(ticker = ticker)

                Text(
                    text = ticker.orEmpty(),
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )

                PercentageChangeText(
                    animationDirection = animationDirection,
                    percentageChangeValue = priceChangeByPercentage,
                    shouldAnimate = shouldAnimatePercentageChange,
                    percentageChangeText = percentageChangeText,
                    onAnimationEnd = onAnimationEnd,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "$exchangeOfLatestTrade | $name",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = Ellipsis,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    text = "$latestTradePriceText ( ${priceChangeInPointsText ?: "0.0"} )",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = Ellipsis,
                )
            }
        }

        Icon(
            modifier = Modifier
                .size(16.dp),
            imageVector = Icons.Outlined.ArrowForwardIos,
            contentDescription = Icons.Outlined.ArrowForwardIos.name,
            tint = MaterialTheme.colorScheme.outline
        )
    }
}

@Preview
@Composable
fun QuoteItemViewPreview() {
    Column {
        QuoteItemView(
            ticker = "AAPL",
            exchangeOfLatestTrade = "NASDAQ",
            name = "Apple Inc.",
            priceChangeByPercentage = 1.46.toBigDecimal(),
            percentageChangeText = "+1.45%",
            latestTradePriceText = "145.00",
            priceChangeInPointsText = "0.5",
            shouldAnimatePercentageChange = true
        )

        Divider()

        QuoteItemView(
            ticker = "GAZP",
            priceChangeByPercentage = (-1.46).toBigDecimal(),
            shouldAnimatePercentageChange = true,
            percentageChangeText = "-1.45%",
            latestTradePriceText = "145.00",
            priceChangeInPointsText = "0.5",
            exchangeOfLatestTrade = "MCX",
            name = "Gazprom",
        )

        Divider()

        QuoteItemView(
            ticker = "YNDEX",
            priceChangeByPercentage = 1.46.toBigDecimal(),
            shouldAnimatePercentageChange = false,
            percentageChangeText = "+1.45%",
            latestTradePriceText = "145.00",
            priceChangeInPointsText = "0.5",
            exchangeOfLatestTrade = "MCX",
            name = "Yandex",
        )

        Divider()

        QuoteItemView(
            ticker = "SBER",
            priceChangeByPercentage = (-1.46).toBigDecimal(),
            shouldAnimatePercentageChange = false,
            percentageChangeText = "-1.45%",
            latestTradePriceText = "145.0014559999",
            priceChangeInPointsText = "0.54444444",
            exchangeOfLatestTrade = "MCX",
            name = "Sberbank Sberbank Sberbank",
        )
    }
}
