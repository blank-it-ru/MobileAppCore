package ru.blank.mobileappcore.core.webview.utils

import ru.blank.mobileappcore.core.webview.WebViewConfig
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UtilsTest {

    @Test
    fun `createViewportInjectionScript generates correct script with default config`() {
        val config = WebViewConfig.default()
        val script = createViewportInjectionScript(config)

        assertContains(script, "meta[name=\"viewport\"]")
        assertContains(script, "document.createElement('meta')")
        assertContains(script, "meta.name = 'viewport'")
        assertContains(script, "width=device-width")
        assertContains(script, "initial-scale=1")
        // При supportZoom = true не должно быть maximum-scale и user-scalable=no
        assertFalse(script.contains("maximum-scale"))
        assertFalse(script.contains("user-scalable=no"))
    }

    @Test
    fun `createViewportInjectionScript includes zoom restrictions when supportZoom is false`() {
        val config = WebViewConfig(
            supportZoom = false,
            useWideViewPort = true,
            initialScale = 1
        )
        val script = createViewportInjectionScript(config)

        assertContains(script, "width=device-width")
        assertContains(script, "initial-scale=1")
        assertContains(script, "maximum-scale=1")
        assertContains(script, "user-scalable=no")
    }

    @Test
    fun `createViewportInjectionScript excludes width when useWideViewPort is false`() {
        val config = WebViewConfig(
            supportZoom = true,
            useWideViewPort = false,
            initialScale = 1
        )
        val script = createViewportInjectionScript(config)

        assertFalse(script.contains("width=device-width"))
        assertContains(script, "initial-scale=1")
    }

    @Test
    fun `createViewportInjectionScript uses custom initialScale value`() {
        val config = WebViewConfig(
            supportZoom = false,
            useWideViewPort = true,
            initialScale = 50
        )
        val script = createViewportInjectionScript(config)

        assertContains(script, "initial-scale=50")
        assertContains(script, "maximum-scale=50")
    }

    @Test
    fun `configureDataStore returns nonPersistentDataStore when clearCookies is true`() {
        val config = WebViewConfig(clearCookies = true)
        val dataStore = configureDataStore(config)
        assertTrue(dataStore.isPersistent().not())
    }

    @Test
    fun `configureDataStore returns defaultDataStore when clearCookies is false`() {
        val config = WebViewConfig(clearCookies = false)
        val dataStore = configureDataStore(config)
        assertTrue(dataStore.isPersistent())
    }

    @Test
    fun `clearCacheIfNeeded does not throw when clearCache is true`() {
        val config = WebViewConfig(clearCache = true)
        clearCacheIfNeeded(config)
    }

    @Test
    fun `clearCacheIfNeeded does not throw when clearCache is false`() {
        val config = WebViewConfig(clearCache = false)
        clearCacheIfNeeded(config)
    }
}
