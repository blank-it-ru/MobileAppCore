package ru.blank.mobileappcore.navigation.core

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.blank.mobileappcore.feature.splashScreen.router.FeatureEntrySplash
import ru.blank.mobileappcore.navigation.api.GraphBuilder
import ru.blank.mobileappcore.navigation.api.entry.AggregateFeatureEntry
import ru.blank.mobileappcore.navigation.api.entry.AggregateTransitionFeatureEntry
import ru.blank.mobileappcore.navigation.api.entry.ComposableFeatureEntry
import ru.blank.mobileappcore.navigation.api.entry.ComposableTransitionFeatureEntry
import ru.blank.mobileappcore.navigation.api.entry.FeatureEntry
import ru.blank.mobileappcore.navigation.api.entry.utils.entry
import ru.blank.mobileappcore.navigation.api.entry.utils.entryTransition
import ru.blank.mobileappcore.navigation.api.rememberNavigationAnimator

class GraphBuilderCore(
    private val entries: Set<Lazy<FeatureEntry<*>>>,
) : GraphBuilder {
    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    override fun build(navController: NavHostController, isEnabledAnimation: Boolean) {
        val navigationAnimator = rememberNavigationAnimator(isEnabledAnimation)
        SharedTransitionLayout {
            NavHost(
                modifier = Modifier.fillMaxSize(),
                startDestination = FeatureEntrySplash.Route,
                navController = navController,
                enterTransition = { navigationAnimator.fadeInAnimation },
                exitTransition = { navigationAnimator.fadeOutAnimation },
                popEnterTransition = { navigationAnimator.fadeInAnimation },
                popExitTransition = { navigationAnimator.fadeOutAnimation }
            ) {
                entries.forEach {
                    when (val entry = it.value) {
                        is ComposableFeatureEntry<*> -> {
                            entry(
                                feature = entry,
                                route = entry.route,
                                navController = navController,
                                animation = entry.animation,
                                navigationAnimator = navigationAnimator,
                            )
                        }

                        is ComposableTransitionFeatureEntry<*> -> {
                            entryTransition(
                                feature = entry,
                                route = entry.route,
                                navController = navController,
                                animation = entry.animation,
                                navigationAnimator = navigationAnimator,
                                sharedTransitionScope = this@SharedTransitionLayout,
                            )
                        }

                        is AggregateFeatureEntry<*> -> {
                            with(entry) {
                                navigation(
                                    navController = navController,
                                    animator = navigationAnimator,
                                )
                            }
                        }

                        is AggregateTransitionFeatureEntry<*> -> {
                            with(entry) {
                                navigation(
                                    navController = navController,
                                    animator = navigationAnimator,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                )
                            }
                        }

                        else -> throw IllegalArgumentException("Unsupported FeatureEntry: $entry")
                    }
                }
            }
        }
    }
}

