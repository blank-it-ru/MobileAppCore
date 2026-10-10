package ru.blank.mobileappcore.feature.splashScreen.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import ru.blank.mobileappcore.core.mvi.compose.ComposableEventHandler
import ru.blank.mobileappcore.feature.splashScreen.presentation.core.Event
import ru.blank.mobileappcore.feature.splashScreen.presentation.core.SplashViewModel

@Composable
internal fun Screen(
    viewModel: SplashViewModel,
    navigateToNextScreen: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HandleEvents(
        events = viewModel.events,
        navigateToNextScreen = navigateToNextScreen
    )

    Content(
        state = state,
        send = viewModel::sendIntent
    )
}

@Composable
private fun HandleEvents(
    events: Flow<Event>,
    navigateToNextScreen: () -> Unit
) {
    ComposableEventHandler(
        events = events,
    ) { event ->
        when (event) {
            is Event.NavigateToNextScreen -> navigateToNextScreen()
        }
    }
}

