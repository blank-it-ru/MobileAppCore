package ru.blank.mobileappcore

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import ru.blank.mobileappcore.navigation.api.GraphBuilder

@Composable
fun App(
    graphBuilder: GraphBuilder,
    isEnabledAnimation: Boolean = true,
) {
    val navController = rememberNavController()

    graphBuilder.build(
        navController = navController,
        isEnabledAnimation = isEnabledAnimation
    )
}

