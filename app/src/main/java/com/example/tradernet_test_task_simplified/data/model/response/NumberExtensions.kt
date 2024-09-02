package com.example.tradernet_test_task_simplified.data.model.response

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.roundToMinStep(minStep: BigDecimal?): String? {
    if (this == BigDecimal.ZERO) return null
    return if (minStep == null || minStep == BigDecimal.ZERO) {
        this.toPlainString()
    } else {
        val roundedValue = this.divide(minStep, 0, RoundingMode.HALF_UP).multiply(minStep)
        roundedValue.formatWithDigits(minStep.getFractionDigits())
    }
}

fun BigDecimal?.getFractionDigits() = this?.scale() ?: 2

fun BigDecimal.formatWithDigits(digitCount: Int): String =
    this.setScale(digitCount, RoundingMode.HALF_UP).toPlainString()