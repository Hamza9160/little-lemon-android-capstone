package com.littlelemon.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.littlelemon.app.data.UserPreferencesRepository
import com.littlelemon.app.ui.screens.HomeScreen
import com.littlelemon.app.ui.screens.OnboardingScreen
import com.littlelemon.app.ui.screens.ProfileScreen

/**
 * Stack navigation host.
 *
 * The start destination is decided once, from persisted state:
 * a returning (registered) user lands on Home, a first-time user on Onboarding.
 * Home -> Profile is a normal push, so the system/Compose back button returns to Home.
 */
@Composable
fun AppNavigation(repository: UserPreferencesRepository) {
    val navController = rememberNavController()

    // Read persisted profile once to choose where to start.
    val startState by produceState<StartState>(initialValue = StartState.Loading) {
        repository.userProfile.collect { profile ->
            value = StartState.Ready(
                if (profile.isRegistered) Destinations.HOME else Destinations.ONBOARDING
            )
        }
    }

    val state = startState
    if (state !is StartState.Ready) return // brief splash while DataStore loads

    NavHost(navController = navController, startDestination = state.startRoute) {
        composable(Destinations.ONBOARDING) {
            OnboardingScreen(
                repository = repository,
                onRegistered = {
                    navController.navigate(Destinations.HOME) {
                        // Remove onboarding from the back stack after registering.
                        popUpTo(Destinations.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }
        composable(Destinations.HOME) {
            HomeScreen(
                onProfileClick = { navController.navigate(Destinations.PROFILE) }
            )
        }
        composable(Destinations.PROFILE) {
            ProfileScreen(
                repository = repository,
                onBack = { navController.popBackStack() },
                onLoggedOut = {
                    navController.navigate(Destinations.ONBOARDING) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}

private sealed interface StartState {
    data object Loading : StartState
    data class Ready(val startRoute: String) : StartState
}
