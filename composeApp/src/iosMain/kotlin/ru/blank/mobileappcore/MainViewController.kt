package ru.blank.mobileappcore

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.compose.koinInject
import ru.blank.mobileappcore.navigation.api.GraphBuilder

fun MainViewController() = ComposeUIViewController {
    val graphBuilder = koinInject<GraphBuilder>()

    App(
        graphBuilder = graphBuilder,
    )
}

