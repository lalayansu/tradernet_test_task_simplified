package com.example.tradernet_test_task_simplified.data.model.response

import java.math.BigDecimal

data class Quote(
    val ticker: String? = null,
    val priceChangeInPoints: BigDecimal? = null,
    val latestTradePrice: BigDecimal? = null,
    val exchangeOfLatestTrade: String? = null,
    val name: String? = null,
    val minStep: BigDecimal? = null,
    val priceChangeByPercentage: BigDecimal? = null,
    val shouldAnimatePercentageChange: Boolean? = null,
    val percentageChangeText: String? = null,
    val priceChangeInPointsText: String? = priceChangeInPoints?.roundToMinStep(minStep),
    val latestTradePriceText: String? = latestTradePrice?.roundToMinStep(minStep),
    val animationDirection: AnimationDirection = AnimationDirection.NONE
) {
    fun mergeWith(newData: Quote) = copy(
        ticker = newData.ticker.compareStrings(ticker),
        exchangeOfLatestTrade = newData.exchangeOfLatestTrade.compareStrings(exchangeOfLatestTrade),
        name = newData.name.compareStrings(name),
        minStep = newData.minStep.compareBigDecimals(minStep),
        priceChangeByPercentage = newData.priceChangeByPercentage.compareBigDecimals(
            priceChangeByPercentage
        ),
        latestTradePrice = newData.latestTradePrice.compareBigDecimals(latestTradePrice),
        priceChangeInPoints = newData.priceChangeInPoints.compareBigDecimals(priceChangeInPoints),
        shouldAnimatePercentageChange = newData.priceChangeByPercentage?.isAnimationNeeded(),
        percentageChangeText = mergePercentageChangeText(newData.priceChangeByPercentage),
        priceChangeInPointsText = newData.priceChangeInPoints?.roundToMinStep(minStep)
            .compareStrings(priceChangeInPoints?.roundToMinStep(minStep)),
        latestTradePriceText = newData.latestTradePrice?.roundToMinStep(minStep)
            .compareStrings(latestTradePrice?.roundToMinStep(minStep)),
        animationDirection = newData.latestTradePrice.compareAndDefineAnimationDirection(
            latestTradePrice
        )
    )

    private fun BigDecimal?.compareAndDefineAnimationDirection(oldValue: BigDecimal?) =
        if (this != null && oldValue != null) when {
            oldValue > this -> AnimationDirection.DOWN
            oldValue < this -> AnimationDirection.UP
            else -> AnimationDirection.NONE
        } else AnimationDirection.NONE

    private fun BigDecimal?.isAnimationNeeded(): Boolean =
        this != null && this != BigDecimal.ZERO && this != this@Quote.priceChangeByPercentage

    private fun mergePercentageChangeText(newPriceChangeByPercentage: BigDecimal?) =
        if (newPriceChangeByPercentage == null || newPriceChangeByPercentage == BigDecimal.ZERO) {
            this.percentageChangeText
        } else {
            newPriceChangeByPercentage.let { percent ->
                if (percent > BigDecimal.ZERO) "+$percent%" else "$percent%"
            }
        }

    private fun String?.compareStrings(oldString: String?) =
        takeIf { string -> !string.isNullOrBlank() && string != oldString } ?: oldString

    private fun BigDecimal?.compareBigDecimals(oldValue: BigDecimal?) =
        takeIf { double ->
            double != null && double != BigDecimal.ZERO && double != oldValue
        } ?: oldValue
}

enum class AnimationDirection {
    UP, DOWN, NONE
}