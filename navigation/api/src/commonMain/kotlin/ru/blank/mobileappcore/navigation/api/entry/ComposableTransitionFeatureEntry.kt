package ru.blank.mobileappcore.navigation.api.entry

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

interface ComposableTransitionFeatureEntry<T : @Serializable Any> : FeatureEntry<T> {

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun Composable(
        navController: NavHostController,
        backStackEntry: NavBackStackEntry,
        sharedTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope,
    )
}

