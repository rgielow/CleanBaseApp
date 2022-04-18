package com.gielow.cleanbaseapp.domain.model

data class CryptoPrices(
    val buy: String,
    val date: String,
    val high: String,
    val last: String,
    val low: String,
    val opening: String,
    val sell: String,
    val vol: String
) {
    companion object {
        fun mock() = CryptoPrices(
            buy = "R$ 200000.00",
            date = "16/04/2022",
            high = "R$ 202000.00",
            last = "R$ 210000.00",
            low = "R$ 193000.00",
            opening = "R$ 203000.00",
            sell = "R$ 190000.00",
            vol = "8.680000"
        )
    }
}