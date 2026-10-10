package ru.blank.mobileappcore.core.webview.utils

import platform.Foundation.NSDate
import platform.Foundation.distantPast
import platform.WebKit.*
import ru.blank.mobileappcore.core.webview.WebViewConfig


/**
 * Генерирует содержимое viewport meta-тега на основе конфигурации WebView.
 * Используется для применения параметров supportZoom, useWideViewPort и initialScale
 */
private fun generateViewportMetaTag(config: WebViewConfig): String {
    val parts = mutableListOf<String>()

    if (config.useWideViewPort) {
        parts.add("width=device-width")
    }

    val scale = config.initialScale
    parts.add("initial-scale=$scale")

    if (!config.supportZoom) {
        parts.add("maximum-scale=$scale")
        parts.add("user-scalable=no")
    }

    return parts.joinToString(", ")
}

/**
 * Создает JavaScript код для инжекции viewport meta-тега в HTML страницу.
 * Это позволяет применять параметры конфигурации (supportZoom, useWideViewPort, initialScale)
 * так как прямого API для этих параметров нет.
 */
fun createViewportInjectionScript(config: WebViewConfig): String {
    val viewportContent = generateViewportMetaTag(config)
    return """
        (function() {
            var meta = document.querySelector('meta[name="viewport"]');
            if (!meta) {
                meta = document.createElement('meta');
                meta.name = 'viewport';
                document.getElementsByTagName('head')[0].appendChild(meta);
            }
            meta.content = '$viewportContent';
        })();
    """.trimIndent()
}

/**
 * Настраивает WKWebsiteDataStore на основе конфигурации WebView.
 */
fun configureDataStore(config: WebViewConfig): WKWebsiteDataStore {
    return if (config.clearCookies) {
        WKWebsiteDataStore.nonPersistentDataStore()
    } else {
        WKWebsiteDataStore.defaultDataStore()
    }
}

/**
 * Очищает кеш WebView , если это указано в конфигурации.
 */
fun clearCacheIfNeeded(config: WebViewConfig) {
    if (config.clearCache) {
        val dataStore = WKWebsiteDataStore.defaultDataStore()
        val dataTypes = setOf(
            WKWebsiteDataTypeMemoryCache,
            WKWebsiteDataTypeDiskCache
        )

        dataStore.removeDataOfTypes(
            dataTypes = dataTypes,
            modifiedSince = NSDate.distantPast()
        ){}
    }
}
