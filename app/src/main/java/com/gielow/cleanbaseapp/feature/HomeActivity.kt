package com.gielow.cleanbaseapp.feature

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.gielow.cleanbaseapp.R
import com.gielow.cleanbaseapp.commons.navigation.setNavigationContent
import com.gielow.cleanbaseapp.feature.HomeViewModel.Navigation
import com.gielow.cleanbaseapp.feature.welcome.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val flowViewModel: HomeViewModel by viewModels()

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
            WelcomeScreen()
        }
    }

    private fun navEvent(navController: NavHostController, navScreen: Navigation) {
        navController.navigate(route = navScreen.route) {

        }
    }

}