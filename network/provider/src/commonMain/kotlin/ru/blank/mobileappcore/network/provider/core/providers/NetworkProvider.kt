package ru.blank.mobileappcore.network.provider.core.providers

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.blank.mobileappcore.network.provider.AuthStateHolder
import ru.blank.mobileappcore.network.provider.core.AuthInterceptor
import ru.blank.mobileappcore.network.provider.core.TokenStorage
import ru.blank.mobileappcore.network.utils.plugins.installInternetConnectionChecker

/**
 * Основной провайдер сетевых функций для работы с Ktorfit и HttpClient.
 *
 * Предоставляет настройки:
 * - JSON-сериализация через Kotlinx Serialization
 * - Таймауты соединения
 * - Проверка интернет-соединения
 * - Логирование в debug-режиме
 *
 * ## Пример использования:
 * ```kotlin
 * val provider = NetworkProvider(isDebug = true, internetConnectionChecker = checker)
 * val ktorfit = provider.provideKtorfit("https://api.example.com")
 * val api = ktorfit.create<MyApi>()
 * ```
 *
 * @property isDebug Флаг режима отладки (включает логирование)
 * @property isHasInternet Флаг доступности интернет соединения
 */
class NetworkProvider(
    private val isDebug: Boolean = false,
    private val isHasInternet: Boolean = true,
    tokenStorage: TokenStorage? = null,
    private val refreshClientProvider: RefreshClientProvider? = null

) {
    /**
     * Настройки JSON-сериализатора:
     * - Игнорирует неизвестные ключи
     * - Красивый вывод (prettyPrint)
     * - Ленивый парсинг
     * - Сериализует значения по умолчанию
     */
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
        encodeDefaults = true
    }


    /**
     * Интерсептор аутентификации для автоматического управления токенами.
     */
    private val authInterceptor = if (tokenStorage != null && refreshClientProvider != null) {
        AuthInterceptor(
            tokenStorage = tokenStorage,
            refreshCall = refreshClientProvider.createRefreshTokenCall(),
            onUnauthorized = { AuthStateHolder.emitUnauthorized() },
            publicPathChecker = { path ->
                path.contains("auth/", ignoreCase = true)
            }
        )
    } else null

    private val clientDefault = HttpClient {
        install(HttpTimeout) {
            requestTimeoutMillis = TIMEOUT
            connectTimeoutMillis = CONNECT_TIMEOUT
            socketTimeoutMillis = TIMEOUT
        }
        install(ContentNegotiation) {
            json(json = json, contentType = ContentType.Application.Json)
        }
        installInternetConnectionChecker(isHasInternet)

         authInterceptor?.also { install(it.install()) }

        if (isDebug) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        // TODO: Реализовать логгирование (например, через Timber)
                    }
                }
                level = LogLevel.ALL
            }
        }
    }

    fun provideKtorfit(
        host: String,
        client: HttpClient? = null,
    ): Ktorfit = Ktorfit.Builder()
        .baseUrl(host)
        .httpClient(client ?: clientDefault)
        .build()


    fun provideKtorfitWithoutHost(
        client: HttpClient? = HttpClient(CIO),
    ): Ktorfit = Ktorfit.Builder()
        .httpClient(client ?: clientDefault)
        .build()

    companion object {
        const val CONNECT_TIMEOUT = 20_000L
        const val TIMEOUT = 60_000L
    }
}

