package com.gielow.cleanbaseapp.feature

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.gielow.cleanbaseapp.R
import com.gielow.cleanbaseapp.commons.navigation.composeNavigate
import com.gielow.cleanbaseapp.commons.navigation.setNavigationContent
import com.gielow.cleanbaseapp.feature.MainFlowViewModel.Navigation
import com.gielow.cleanbaseapp.feature.cryptoprices.CryptoPricesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val flowViewModel: MainFlowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.white)
        setNavigationContent(
            startDestination = Navigation.Home.route,
            navGraphBuilder = this::navGraphBuilder,
            navEventFlow = flowViewModel.eventsFlow,
            navEvent = this::navEvent
        )
    }

    private fun navGraphBuilder(builder: NavGraphBuilder) = builder.apply {
        composable(Navigation.Home.route) {
            CryptoPricesScreen()
        }
    }

    private fun navEvent(navController: NavHostController, navScreen: Navigation) {
        when (navScreen) {
            is Navigation.Home -> navController.composeNavigate(
                route = navScreen.route,
                popStack = navScreen.popStack
            )
        }
    }

}