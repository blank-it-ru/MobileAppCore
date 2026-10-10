package ru.blank.mobileappcore.feature.splashScreen.presentation.core

import ru.blank.mobileappcore.core.mvi.core.BaseViewModel
import ru.blank.mobileappcore.core.mvi.core.ReduceResult

private typealias Result = ReduceResult<UiState, Event, Effect>

internal class SplashViewModel : BaseViewModel<UiState, Intent, Event, Effect>(
    initialState = UiState()
) {
    override fun handleIntent(intent: Intent, state: UiState): Result = when (intent) {
        is Intent.OnNavigateToNextScreen -> onNavigateToNextScreen(state)
    }

    private fun onNavigateToNextScreen(state: UiState): Result {
        return Result(state)
    }
}

