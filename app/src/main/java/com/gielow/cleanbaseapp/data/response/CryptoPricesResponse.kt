package com.gielow.cleanbaseapp.data.response

import com.google.gson.annotations.SerializedName

data class CryptoPricesResponse(
    @SerializedName("ticker") val ticker: Ticker
) {
    companion object {
        fun mock() = CryptoPricesResponse(
            ticker = Ticker.mock()
        )
    }
}