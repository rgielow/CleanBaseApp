package com.gielow.cleanbaseapp.commons.navigation

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.gielow.cleanbaseapp.R
import kotlinx.coroutines.flow.Flow

fun <T> ComponentActivity.setNavigationContent(
    startDestination: String,
    navGraphBuilder: NavGraphBuilder.() -> Unit,
    navEventFlow: Flow<T>,
    navEvent: (navController: NavHostController, navScreen: T) -> Unit
) {
    setContent {
        val navController = rememberNavController()
        LaunchedEffect(Unit) {
            navEventFlow.collect { navEvent(navController, it) }
        }
        NavHost(
            navController = navController,
            startDestination = startDestination,
            builder = navGraphBuilder
        )
    }

}

fun NavController.composeNavigate(
    route: String,
    popStack: Boolean = false,
    launchSingleTop: Boolean = true
) = navigate(route = route) {
    val startDestinationRoute = graph.startDestinationRoute.orEmpty()
    if (popStack && startDestinationRoute.isNotEmpty()) {
        popUpTo(startDestinationRoute) { inclusive = true }
    }
    this.launchSingleTop = launchSingleTop
}
