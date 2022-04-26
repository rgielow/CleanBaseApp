package com.gielow.cleanbaseapp.feature

import com.gielow.cleanbaseapp.common.CoroutinesTestRule
import com.gielow.cleanbaseapp.commons.network.Result
import com.gielow.cleanbaseapp.domain.model.CryptoPrices
import com.gielow.cleanbaseapp.domain.usecase.GetCryptoPricesUseCase
import com.gielow.cleanbaseapp.feature.cryptoprices.CryptoPricesViewModel
import com.gielow.cleanbaseapp.feature.cryptoprices.CryptoPricesViewModel.ScreenEvent
import com.gielow.cleanbaseapp.feature.cryptoprices.CryptoPricesViewModel.ScreenState
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CryptoPricesViewModelTest {

    @get:Rule
    var mainCoroutineRule = CoroutinesTestRule()


    private val screenEvent: (ScreenEvent) -> Unit = mockk(relaxed = true)
    private val screenState = mockk<(ScreenState) -> Unit>(relaxed = true)
    private val useCase: GetCryptoPricesUseCase = mockk(relaxed = true)

    private lateinit var viewModel: CryptoPricesViewModel
    private val testCoroutineScope = TestCoroutineScope()

    @Before
    fun setup() {
        viewModel = CryptoPricesViewModel(useCase)
        setupObservers()
    }

    @After
    fun clear() {
        testCoroutineScope.cancel()
    }

    @Test
    fun `when on close clicked then Finish event is invoke`(): Unit = runTest {
        viewModel.onCloseClicked()

        verify { screenEvent.invoke(ScreenEvent.Finish) }
    }

    @Test
    fun `when updatePrices() is success then ScreenSuccess is invoke`(): Unit = runBlocking {
        //Prepare
        coEvery { useCase.execute() } returns Result.Success(CRYPTO_MOCK)

        //Action
        viewModel.updatePrices()

        //Check
        verifyOrder {
            screenState.invoke(ScreenState.ScreenLoading)
            screenState.invoke(ScreenState.ScreenSuccess(CRYPTO_MOCK))
        }
    }

    @Test
    fun `when updatePrices() is error then ScreenError is invoke`(): Unit = runBlocking {
        //Prepare
        coEvery { useCase.execute() } returns Result.Failure(ERROR_MOCK)

        //Action
        viewModel.updatePrices()

        //Check
        verifyOrder {
            screenState.invoke(ScreenState.ScreenLoading)
            screenState.invoke(ScreenState.ScreenError(ERROR_MOCK))
        }
    }


    //Helpers
    private fun setupObservers() = testCoroutineScope.run {
        launch { viewModel.eventsFlow.collect { screenEvent.invoke(it) } }
        launch { viewModel.uiState.screenState.collect { screenState.invoke(it) } }
    }

    companion object {
        private val CRYPTO_MOCK = CryptoPrices.mock()
        private const val ERROR_MOCK = "error"
    }
}