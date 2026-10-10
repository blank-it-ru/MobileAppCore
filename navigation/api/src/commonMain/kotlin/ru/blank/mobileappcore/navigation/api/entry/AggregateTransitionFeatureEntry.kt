package ru.blank.mobileappcore.navigation.api.entry

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable
import ru.blank.mobileappcore.navigation.api.NavigationAnimator

interface AggregateTransitionFeatureEntry<T : @Serializable Any> : FeatureEntry<T> {

    @OptIn(ExperimentalSharedTransitionApi::class)
    fun NavGraphBuilder.navigation(
        navController: NavHostController,
        animator: NavigationAnimator,
        sharedTransitionScope: SharedTransitionScope,
    )
}

