package com.gielow.cleanbaseapp.commons.extentions

import java.text.DecimalFormat
import java.util.Locale
import kotlin.math.roundToInt

fun String.toBrCurrency(): String {
    val value = this.toBigDecimal()
    return getDecimalFormat().format(value.toDouble())

}

fun String.roundTwoDigits(): String {
    return ((this.toDouble() * 100).roundToInt().toDouble() / 100).toString()
}

private fun getDecimalFormat() = DecimalFormat.getCurrencyInstance(Locale("pt", "BR"))