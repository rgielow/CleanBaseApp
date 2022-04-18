package com.gielow.cleanbaseapp.data.api

import com.gielow.cleanbaseapp.data.response.CryptoPricesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoApi {
    @GET("{cryptoName}/ticker/")
    suspend fun getPrices(
        @Path("cryptoName") cryptoName: String
    ): Response<CryptoPricesResponse>
}