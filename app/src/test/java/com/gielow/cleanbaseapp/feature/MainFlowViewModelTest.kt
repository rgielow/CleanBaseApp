package com.gielow.cleanbaseapp.feature

import com.gielow.cleanbaseapp.common.CoroutinesTestRule
import com.gielow.cleanbaseapp.feature.MainFlowViewModel.Navigation
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainFlowViewModelTest {

    @get:Rule
    var mainCoroutineRule = CoroutinesTestRule()

    // for some reason TestScope() doesn't work for collect flow, so for now I'll use TestCoroutineScope()
    private val testCoroutineScope = TestCoroutineScope()

    private val observerNavigation: (Navigation) -> (Unit) = mockk(relaxed = true)
    private lateinit var viewModelTest: MainFlowViewModel

    @Before
    fun `setup test`() {
        viewModelTest = MainFlowViewModel()
        setupObservers()
    }

    @Test
    fun `when navigate is called to Home, then call welcome navigation event`() = runTest {
        //given
        val navigation = Navigation.Home
        //when
        viewModelTest.navigate(navigation)
        //then
        coVerify { observerNavigation(navigation) }
    }

    //Helper
    private fun setupObservers() = testCoroutineScope.run {
        launch { viewModelTest.eventsFlow.collect { observerNavigation(it) } }
    }
}