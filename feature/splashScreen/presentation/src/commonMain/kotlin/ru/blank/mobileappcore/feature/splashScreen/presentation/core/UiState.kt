package ru.blank.mobileappcore.feature.splashScreen.presentation.core

import ru.blank.mobileappcore.core.mvi.label.BaseEffect
import ru.blank.mobileappcore.core.mvi.label.BaseEvent
import ru.blank.mobileappcore.core.mvi.label.BaseIntent
import ru.blank.mobileappcore.core.mvi.label.BaseState

internal data class UiState(
    val isLoading: Boolean = false,
) : BaseState

internal sealed interface Intent : BaseIntent {
    data object OnNavigateToNextScreen : Intent
}

internal sealed interface Effect : BaseEffect

internal sealed interface Event : BaseEvent {
    data object NavigateToNextScreen : Event
}

