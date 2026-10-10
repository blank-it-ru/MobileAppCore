package ru.blank.mobileappcore.feature.splashScreen.router

import kotlinx.serialization.Serializable
import ru.blank.mobileappcore.navigation.api.entry.ComposableFeatureEntry

abstract class FeatureEntrySplash : ComposableFeatureEntry<FeatureEntrySplash.Route> {
    override val route: Route = Route

    @Serializable
    data object Route
}

