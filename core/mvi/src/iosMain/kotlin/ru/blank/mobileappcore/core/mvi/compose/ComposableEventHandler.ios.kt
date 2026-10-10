package ru.blank.mobileappcore.core.mvi.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
actual fun <T> ComposableEventHandler(
    events: Flow<T>,
    handle: (T) -> Unit
) {
    LaunchedEffect(Unit) {
        launch {
            events.collect { effect ->
                handle.invoke(effect)
            }
        }
    }
}

