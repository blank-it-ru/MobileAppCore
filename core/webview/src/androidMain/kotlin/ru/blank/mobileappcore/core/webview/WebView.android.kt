package ru.blank.mobileappcore.core.webview

import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient as AndroidWebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import ru.blank.mobileappcore.core.logger.Log

private const val TAG = "WebView"

@Composable
actual fun WebView(
    url: String,
    onUrlLoading: (String) -> Boolean,
    modifier: Modifier,
    config: WebViewConfig
) {
    if (url.isEmpty()) {
        Log.d(TAG, "URL is empty, WebView will not be created.")
        return
    }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    loadUrl(url)
                    if (config.clearCache) {
                        clearCache(true)
                    }
                    if (config.clearHistory) {
                        clearHistory()
                    }

                    if (config.clearCookies) {
                        val cookieManager = CookieManager.getInstance()
                        cookieManager.removeAllCookies(null)
                        cookieManager.flush()
                    }

                    settings.apply {
                        javaScriptEnabled = config.javaScriptEnabled
                        domStorageEnabled = config.domStorageEnabled
                        setSupportZoom(config.supportZoom)
                        useWideViewPort = config.useWideViewPort
                        allowContentAccess = config.allowContentAccess
                        setInitialScale(config.initialScale)
                        builtInZoomControls = config.builtInZoomControls
                    }

                    webViewClient = object : AndroidWebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            val url = request?.url.toString()
                            Log.d(TAG, "Loading URL: $url")
                            return onUrlLoading(url)
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            url?.let { onUrlLoading(it) }
                        }
                    }

                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            super.onProgressChanged(view, newProgress)
                            Log.d(TAG, "Loading progress: $newProgress%")
                        }
                    }

                    isClickable = config.isClickable
                    isFocusable = config.isFocusable
                    isFocusableInTouchMode = config.isFocusableInTouchMode
                }
            },
            update = { webView ->
                webView.loadUrl(url)
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
