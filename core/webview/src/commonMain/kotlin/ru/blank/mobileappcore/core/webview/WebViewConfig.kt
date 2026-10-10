package ru.blank.mobileappcore.core.webview

/**
 * Конфигурация для WebView компонента.
 *
 * @param clearCache
 * @param clearHistory
 * @param clearCookies
 * @param javaScriptEnabled
 * @param domStorageEnabled
 * @param supportZoom
 * @param useWideViewPort Использовать ли широкий viewport
 * @param allowContentAccess Разрешен ли доступ к контенту
 * @param initialScale Начальный масштаб (1-100)
 * @param builtInZoomControls Встроенные элементы управления зумом
 * @param isClickable
 * @param isFocusable
 * @param isFocusableInTouchMode
 * @param isInteractive Интерактивен ли элемент (iOS)
 * @param isNativeAccessibilityEnabled Включена ли нативная доступность (iOS)
 */
data class WebViewConfig(
    val clearCache: Boolean = true,
    val clearHistory: Boolean = true,
    val clearCookies: Boolean = true,
    val javaScriptEnabled: Boolean = true,
    val domStorageEnabled: Boolean = true,
    val supportZoom: Boolean = true,
    val useWideViewPort: Boolean = true,
    val allowContentAccess: Boolean = true,
    val initialScale: Int = 1,
    val builtInZoomControls: Boolean = true,
    val isClickable: Boolean = true,
    val isFocusable: Boolean = true,
    val isFocusableInTouchMode: Boolean = true,
    val isInteractive: Boolean = true,
    val isNativeAccessibilityEnabled: Boolean = true
) {
    companion object {
        /**
         * Дефолтная конфигурация с текущими настройками по умолчанию.
         */
        fun default(): WebViewConfig = WebViewConfig()
    }
}
