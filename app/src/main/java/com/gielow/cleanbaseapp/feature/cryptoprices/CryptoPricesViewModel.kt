package com.gielow.cleanbaseapp.feature.cryptoprices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gielow.cleanbaseapp.commons.network.Result
import com.gielow.cleanbaseapp.commons.viewModel.ChannelEventSenderImpl
import com.gielow.cleanbaseapp.commons.viewModel.EventSender
import com.gielow.cleanbaseapp.domain.model.CryptoPrices
import com.gielow.cleanbaseapp.domain.usecase.GetCryptoPricesUseCase
import com.gielow.cleanbaseapp.feature.cryptoprices.CryptoPricesViewModel.ScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private const val EMPTY = ""
private const val INITIAL_PROGRESS = 0F
private const val TIME_TO_REFRESH = 10000L
private const val DELAY = 50L

@HiltViewModel
class CryptoPricesViewModel @Inject constructor(
    private val getCryptoPricesUseCase: GetCryptoPricesUseCase
) : ViewModel(), EventSender<ScreenEvent> by ChannelEventSenderImpl() {
    val uiState = UiState()


    fun onCloseClicked() = viewModelScope.sendEvent(ScreenEvent.Finish)

    fun refreshPrices() {
        uiState.screenState.value = ScreenState.ScreenLoading
        viewModelScope.launch {
            when (val result = getCryptoPricesUseCase.execute()) {
                is Result.Success -> result.data?.let {
                    setSuccess(it)
                }
                is Result.Failure -> uiState.screenState.value =
                    ScreenState.ScreenError(result.error)
            }
        }
    }

    private fun setSuccess(crypto: CryptoPrices) {
        uiState.apply {
            buy.value = crypto.buy
            date.value = crypto.date
            high.value = crypto.high
            last.value = crypto.last
            low.value = crypto.low
            opening.value = crypto.opening
            sell.value = crypto.sell
            vol.value = crypto.vol
            screenState.value = ScreenState.ScreenSuccess(crypto = crypto)
        }
        timerToRefresh()
    }

    private fun timerToRefresh() {
        var currentTimer = INITIAL_PROGRESS
        uiState.progress.value = INITIAL_PROGRESS
        viewModelScope.launch {
            while (TIME_TO_REFRESH > currentTimer) {
                delay(DELAY)
                currentTimer += DELAY
                uiState.progress.value = currentTimer / TIME_TO_REFRESH
            }
        }.invokeOnCompletion { refreshPrices() }
    }


    class UiState {
        val screenState = MutableStateFlow<ScreenState>(ScreenState.ScreenLoading)
        val buy = MutableStateFlow(EMPTY)
        val date = MutableStateFlow(EMPTY)
        val high = MutableStateFlow(EMPTY)
        val last = MutableStateFlow(EMPTY)
        val low = MutableStateFlow(EMPTY)
        val opening = MutableStateFlow(EMPTY)
        val sell = MutableStateFlow(EMPTY)
        val vol = MutableStateFlow(EMPTY)
        val progress = MutableStateFlow(INITIAL_PROGRESS)
    }

    sealed class ScreenState {
        data class ScreenSuccess(val crypto: CryptoPrices) : ScreenState()
        data class ScreenError(val error: String) : ScreenState()
        object ScreenLoading : ScreenState()
    }

    sealed class ScreenEvent {
        object Finish : ScreenEvent()
    }
}