package ru.blank.mobileappcore.navigation.api.entry

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable
import ru.blank.mobileappcore.navigation.api.NavigationAnimator

interface AggregateFeatureEntry<T : @Serializable Any> : FeatureEntry<T> {

    fun NavGraphBuilder.navigation(
        navController: NavHostController,
        animator: NavigationAnimator,
    )
}

