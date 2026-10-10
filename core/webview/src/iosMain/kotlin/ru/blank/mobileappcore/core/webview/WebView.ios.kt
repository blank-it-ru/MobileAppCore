package ru.blank.mobileappcore.core.webview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCSignatureOverride
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.*
import ru.blank.mobileappcore.core.logger.Log
import platform.darwin.NSObject
import ru.blank.mobileappcore.core.webview.utils.clearCacheIfNeeded
import ru.blank.mobileappcore.core.webview.utils.configureDataStore
import ru.blank.mobileappcore.core.webview.utils.createViewportInjectionScript

private const val TAG = "WebView"
@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun WebView(
    url: String,
    onUrlLoading: (String) -> Boolean,
    modifier: Modifier,
    config: WebViewConfig
) {
    Log.d(TAG, "Received URL: $url")
    if (url.isEmpty()) {
        Log.d(TAG, "URL is empty, WebView will not be created.")
        return
    }

    UIKitView(
        factory = {
            Log.d(TAG, "UIKitView factory: starting WKWebView creation")
            
            clearCacheIfNeeded(config)
            
            val viewportScript = if (config.javaScriptEnabled) {
                createViewportInjectionScript(config)
            } else {
                null
            }
            
            val webViewConfig = WKWebViewConfiguration().apply {
                preferences.javaScriptEnabled = config.javaScriptEnabled
                
                websiteDataStore = configureDataStore(config)

                if (config.javaScriptEnabled && viewportScript != null) {
                    val userContentController = WKUserContentController()
                    val script = WKUserScript(
                        source = viewportScript,
                        injectionTime = WKUserScriptInjectionTime.WKUserScriptInjectionTimeAtDocumentStart,
                        forMainFrameOnly = true
                    )
                    userContentController.addUserScript(script)
                    this.userContentController = userContentController
                }
            }
            WKWebView(frame = CGRectZero.readValue(), configuration = webViewConfig).apply {
                setUserInteractionEnabled(config.isClickable)
                
                navigationDelegate = object : NSObject(), WKNavigationDelegateProtocol {
                    @ObjCSignatureOverride
                    override fun webView(
                        webView: WKWebView,
                        decidePolicyForNavigationAction: WKNavigationAction,
                        decisionHandler: (WKNavigationActionPolicy) -> Unit
                    ) {
                        val url = decidePolicyForNavigationAction.request.URL?.absoluteString ?: ""
                        Log.d(TAG, "Navigation Action: $url")
                        
                        if (onUrlLoading(url)) {
                            decisionHandler(WKNavigationActionPolicy.WKNavigationActionPolicyAllow)
                        } else {
                            decisionHandler(WKNavigationActionPolicy.WKNavigationActionPolicyCancel)
                        }
                    }

                    @ObjCSignatureOverride
                    override fun webView(
                        webView: WKWebView,
                        decidePolicyForNavigationResponse: WKNavigationResponse,
                        decisionHandler: (WKNavigationResponsePolicy) -> Unit
                    ) {
                        Log.d(TAG, "Navigation Response: ${decidePolicyForNavigationResponse.response.URL?.absoluteString}")
                        decisionHandler(WKNavigationResponsePolicy.WKNavigationResponsePolicyAllow)
                    }

                    @ObjCSignatureOverride
                    override fun webView(
                        webView: WKWebView,
                        didStartProvisionalNavigation: WKNavigation?
                    ) {
                        val url = webView.URL?.absoluteString ?: ""
                        Log.d(TAG, "Page loading started: $url")
                    }

                    @ObjCSignatureOverride
                    override fun webView(
                        webView: WKWebView,
                        didFinishNavigation: WKNavigation?
                    ) {
                        val url = webView.URL?.absoluteString ?: ""
                        Log.d(TAG, "Page loaded successfully: $url")

                        if (viewportScript != null && config.javaScriptEnabled) {
                            webView.evaluateJavaScript(viewportScript) { result, error ->
                                if (error != null) {
                                    Log.e(TAG, "Failed to inject viewport script: ${error.localizedDescription}")
                                } else {
                                    Log.d(TAG, "Viewport script injected successfully after page load")
                                }
                            }
                        }
                        
                        onUrlLoading(url)
                    }

                    @ObjCSignatureOverride
                    override fun webView(
                        webView: WKWebView,
                        didFailNavigation: WKNavigation?,
                        withError: platform.Foundation.NSError
                    ) {
                        Log.e(TAG, "Navigation error: ${withError.localizedDescription}")
                    }
                }
                Log.d(TAG, "UIKitView factory: calling loadRequest with URL: $url")
                loadRequest(NSURLRequest(NSURL(string = url)))
            }
        },
        modifier = modifier.fillMaxSize(),
        properties = UIKitInteropProperties(
            isInteractive = config.isFocusable && config.isInteractive,
            isNativeAccessibilityEnabled = config.isNativeAccessibilityEnabled
        )
    )
}
