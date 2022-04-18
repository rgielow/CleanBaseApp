package com.gielow.cleanbaseapp.data.response

import com.google.gson.annotations.SerializedName

data class Ticker(
    @SerializedName("buy") val buy: String,
    @SerializedName("date") val date: Long,
    @SerializedName("high") val high: String,
    @SerializedName("last") val last: String,
    @SerializedName("low") val low: String,
    @SerializedName("open") val opening: String,
    @SerializedName("sell") val sell: String,
    @SerializedName("vol") val vol: String
) {
    companion object {
        fun mock() = Ticker(
            buy = "R$ 200000.00",
            date = 1650117383,
            high = "R$ 202000.00",
            last = "R$ 210000.00",
            low = "R$ 193000.00",
            opening = "R$ 203000.00",
            sell = "R$ 190000.00",
            vol = "8.680000"
        )
    }
}