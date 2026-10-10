package ru.blank.mobileappcore.core.webview

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Multiplatform WebView component
 *
 * @param url URL-адрес для загрузки в WebView.
 * @param onUrlLoading Колбэк для обработки загрузки URL.
 * Возвращает true, если URL должен быть загружен в WebView, false - для обработки внешним способом.
 * @param modifier Modifier для применения к WebView.
 * @param config Конфигурация WebView с настройками поведения и параметров.
 */
@Composable
expect fun WebView(
    url: String,
    onUrlLoading: (String) -> Boolean,
    modifier: Modifier = Modifier,
    config: WebViewConfig = WebViewConfig.default()
)
