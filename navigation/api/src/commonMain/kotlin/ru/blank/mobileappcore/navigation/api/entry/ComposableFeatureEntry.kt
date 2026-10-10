package ru.blank.mobileappcore.navigation.api.entry

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

interface ComposableFeatureEntry<T : @Serializable Any> : FeatureEntry<T> {

    @Composable
    fun Composable(
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    )
}

