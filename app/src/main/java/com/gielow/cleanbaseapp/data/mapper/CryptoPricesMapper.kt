package com.gielow.cleanbaseapp.data.mapper

import com.gielow.cleanbaseapp.commons.extentions.formatAsFullBrazilianDateAnHour
import com.gielow.cleanbaseapp.data.response.CryptoPricesResponse
import com.gielow.cleanbaseapp.domain.model.CryptoPrices

fun CryptoPricesResponse.toCryptoPrices() = CryptoPrices(
    buy = ticker.buy,
    date = ticker.date.formatAsFullBrazilianDateAnHour(),
    high = ticker.high,
    last = ticker.last,
    low = ticker.low,
    opening = ticker.opening,
    sell = ticker.sell,
    vol = ticker.vol
)