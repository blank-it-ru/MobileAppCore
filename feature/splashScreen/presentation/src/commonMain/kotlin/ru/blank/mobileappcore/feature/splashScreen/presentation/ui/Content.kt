package ru.blank.mobileappcore.feature.splashScreen.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.blank.mobileappcore.feature.splashScreen.presentation.core.Intent
import ru.blank.mobileappcore.feature.splashScreen.presentation.core.UiState

@Composable
internal fun Content(
    modifier: Modifier = Modifier,
    state: UiState,
    send: (Intent) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Splash Screen",
        )
    }
}

