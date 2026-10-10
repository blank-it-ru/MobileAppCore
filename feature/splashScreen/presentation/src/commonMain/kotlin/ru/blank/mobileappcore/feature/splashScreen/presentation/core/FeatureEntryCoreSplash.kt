package ru.blank.mobileappcore.feature.splashScreen.presentation.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel
import ru.blank.mobileappcore.feature.splashScreen.presentation.ui.Screen
import ru.blank.mobileappcore.feature.splashScreen.router.FeatureEntrySplash

class FeatureEntryCoreSplash : FeatureEntrySplash() {
    @Composable
    override fun Composable(
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        Screen(
            viewModel = koinViewModel<SplashViewModel>(),
            navigateToNextScreen = { navController.navigateToNextScreen() }
        )
    }

    private fun NavHostController.navigateToNextScreen() {

    }
}

