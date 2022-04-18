package com.gielow.cleanbaseapp.commons.extentions

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

private const val DATE_MULTIPLIER = 1000L
fun Long.formatAsFullBrazilianDateAnHour(): String {
    return SimpleDateFormat(
        DATE_FORMAT_DD_MM_YYYY_HH_MM_SS,
        getLocale()
    ).format(Date(this * DATE_MULTIPLIER))
}

private fun getLocale() = Locale("pt", "BR")