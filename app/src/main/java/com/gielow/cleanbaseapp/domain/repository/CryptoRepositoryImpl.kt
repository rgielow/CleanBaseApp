package com.gielow.cleanbaseapp.domain.repository

import com.gielow.cleanbaseapp.data.api.CryptoApi
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(
    private val api: CryptoApi
) : CryptoRepository {
    override suspend fun getPrices(
        cryptoName: String
    ) = api.getPrices(cryptoName = cryptoName)
}
