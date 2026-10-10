package ru.blank.mobileappcore.network.provider.core

import io.ktor.client.HttpClient
import io.ktor.client.call.HttpClientCall
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.HttpSendPipeline
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.encodedPath
import io.ktor.util.AttributeKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Ktor плагин для автоматической JWT аутентификации с поддержкой refresh токенов.
 * 
 * @param tokenStorage хранилище токенов для чтения и записи access/refresh токенов
 * @param refreshCall функция обновления токенов, принимает refresh токен, возвращает новую пару или null при ошибке
 * @param onUnauthorized callback при окончательной неудаче аутентификации (logout пользователя)
 * @param publicPathChecker функция проверки публичности эндпоинта (true = не требует авторизации)
 */
class AuthInterceptor(
    private val tokenStorage: TokenStorage,
    private val refreshCall: suspend (String) -> TokenPairModel?,
    private val onUnauthorized: suspend () -> Unit,
    private val publicPathChecker: (String) -> Boolean = { false }
) {

    private val refreshMutex = Mutex()
    private var refreshJob: Deferred<Boolean>? = null

    /**
     * Устанавливает плагин в Ktor HttpClient.
     */
    fun install() = object : HttpClientPlugin<Unit, AuthInterceptor> {

        override val key: AttributeKey<AuthInterceptor> =
            AttributeKey("UniversalAuthInterceptor")

        override fun prepare(block: Unit.() -> Unit) = this@AuthInterceptor

        override fun install(plugin: AuthInterceptor, scope: HttpClient) {
            scope.sendPipeline.intercept(HttpSendPipeline.Monitoring) {
                plugin.addToken(context)
                try {
                    val response = proceedWith(subject)
                    if (response is HttpClientCall && response.response.status == HttpStatusCode.Unauthorized) {
                        if (plugin.handleUnauthorized(context)) {
                            proceedWith(subject)
                        }
                    }
                    return@intercept
                } catch (cause: Exception) {
                    val errorResponse = (cause as? ResponseException)?.response
                    if (errorResponse?.status == HttpStatusCode.Unauthorized) {
                        if (plugin.handleUnauthorized(context)) {
                            proceedWith(subject)
                            return@intercept
                        } else {
                            throw cause
                        }
                    }
                    throw cause
                }
            }
        }
    }

    /**
     * Обрабатывает 401 Unauthorized: проверяет публичность ручки, делает refresh токена.
     */
    private suspend fun handleUnauthorized(context: HttpRequestBuilder): Boolean {
        if (publicPathChecker(context.url.encodedPath)) return false

        val refreshed = refreshIfNeeded()
        if (refreshed) {
            addToken(context)
            return true
        }
        return false
    }

    /**
     * Выполняет refresh токена при необходимости.
     * Гарантирует, что только один refresh выполняется одновременно.
     */
    private suspend fun refreshIfNeeded(): Boolean {
        return refreshMutex.withLock {
            val currentJob = refreshJob
            if (currentJob != null) {
                val result = currentJob.await()
                refreshJob = null
                return@withLock result
            }

            val job = CoroutineScope(Dispatchers.IO).async {
                val refreshToken = tokenStorage.refreshToken
                    ?: return@async false.also { logout() }

                return@async try {
                    val newTokens = refreshCall(refreshToken)
                    if (newTokens != null) {
                        tokenStorage.accessToken = newTokens.accessToken
                        tokenStorage.refreshToken = newTokens.refreshToken
                        true
                    } else {
                        logout()
                        false
                    }
                } catch (e: Exception) {
                    false
                }
            }

            refreshJob = job
            val result = job.await()
            refreshJob = null
            result
        }
    }

    /**
     * Добавляет токен в заголовок Authorization для приватных ручек.
     */
    private fun addToken(request: HttpRequestBuilder) {
        if (publicPathChecker(request.url.encodedPath)) return
        tokenStorage.accessToken?.let { token ->
            request.headers[HttpHeaders.Authorization] = "Bearer $token"
        }
    }

    /**
     * Выполняет разлогинивание пользователя.
     */
    private suspend fun logout() {
        tokenStorage.clear()
        onUnauthorized()
    }
} 