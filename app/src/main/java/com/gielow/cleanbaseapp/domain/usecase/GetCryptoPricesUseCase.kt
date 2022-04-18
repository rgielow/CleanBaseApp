package com.gielow.cleanbaseapp.domain.usecase

import com.gielow.cleanbaseapp.commons.network.Result
import com.gielow.cleanbaseapp.data.mapper.toCryptoPrices
import com.gielow.cleanbaseapp.domain.repository.CryptoRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCryptoPricesUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: CryptoRepositoryImpl
) {
    suspend fun execute() = withContext(dispatcher) {
        delay(1000)
        val result = repository.getPrices("BTC")
        return@withContext if (result.isSuccessful) Result.Success(
            result.body()?.toCryptoPrices()
        )
        else Result.Failure(result.message())
    }
}