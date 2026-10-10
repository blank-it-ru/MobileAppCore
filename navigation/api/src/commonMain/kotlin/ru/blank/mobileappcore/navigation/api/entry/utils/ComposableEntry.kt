package ru.blank.mobileappcore.navigation.api.entry.utils

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.ComposeNavigatorDestinationBuilder
import androidx.navigation.get
import ru.blank.mobileappcore.navigation.api.NavigationAnimator
import ru.blank.mobileappcore.navigation.api.entry.ComposableFeatureEntry
import kotlin.reflect.KType

inline fun <reified T : Any> NavGraphBuilder.entry(
    route: T,
    feature: ComposableFeatureEntry<*>,
    navController: NavHostController,
    typeMap: Map<KType, NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    animation: EntryAnimation = EntryAnimation.Default,
    navigationAnimator: NavigationAnimator,
) {
    destination(
        ComposeNavigatorDestinationBuilder(
            navigator = provider[ComposeNavigator::class],
            route = route::class,
            typeMap = typeMap
        ) {
            feature.Composable(navController = navController, backStackEntry = it)
        }.apply {
            deepLinks.forEach { deepLink -> deepLink(deepLink) }

            val enterTransition: EnterTransition? = when (animation) {
                EntryAnimation.Default -> null
                EntryAnimation.BottomSheet -> navigationAnimator.enterFromBottom
                EntryAnimation.Fade -> navigationAnimator.fadeInAnimation
                EntryAnimation.SlideEnterFromRightExitToRight -> navigationAnimator.enterFromRight
            }
            val exitTransition: ExitTransition? = when (animation) {
                EntryAnimation.Default -> null
                EntryAnimation.BottomSheet -> navigationAnimator.exitToBottom
                EntryAnimation.Fade -> navigationAnimator.fadeOutAnimation
                EntryAnimation.SlideEnterFromRightExitToRight -> null
            }

            val popEnterTransition: EnterTransition? = when (animation) {
                EntryAnimation.Default -> null
                EntryAnimation.BottomSheet -> navigationAnimator.enterFromBottom
                EntryAnimation.Fade -> navigationAnimator.fadeInAnimation
                EntryAnimation.SlideEnterFromRightExitToRight -> null
            }

            val popExitTransition: ExitTransition? = when (animation) {
                EntryAnimation.Default -> null
                EntryAnimation.BottomSheet -> navigationAnimator.exitToBottom
                EntryAnimation.Fade -> navigationAnimator.fadeOutAnimation
                EntryAnimation.SlideEnterFromRightExitToRight -> navigationAnimator.exitToRight
            }

            this.enterTransition = { enterTransition }
            this.exitTransition = { exitTransition }
            this.popEnterTransition = { popEnterTransition }
            this.popExitTransition = { popExitTransition }
            this.sizeTransform = sizeTransform
        }
    )
}

