package com.gielow.cleanbaseapp.domain.repository

import com.gielow.cleanbaseapp.data.response.CryptoPricesResponse
import retrofit2.Response

interface CryptoRepository {
    suspend fun getPrices(cryptoName: String): Response<CryptoPricesResponse>
}