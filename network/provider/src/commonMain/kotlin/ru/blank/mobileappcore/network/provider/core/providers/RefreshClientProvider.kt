package ru.blank.mobileappcore.network.provider.core.providers

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.blank.mobileappcore.network.provider.core.TokenPairModel
import ru.blank.mobileappcore.network.provider.core.providers.NetworkProvider.Companion.CONNECT_TIMEOUT
import ru.blank.mobileappcore.network.provider.core.providers.NetworkProvider.Companion.TIMEOUT
import ru.blank.mobileappcore.network.utils.plugins.installInternetConnectionChecker

/**
 * Провайдер для создания HTTP клиента и API специально для refresh токенов.
 *
 * @param baseUrl базовый URL для API
 * @param isHasInternet Флаг доступности интернет соединения
 */
class RefreshClientProvider(
    private val baseUrl: String,
    private val isHasInternet: Boolean = true,
) {

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
        encodeDefaults = true
    }

    /**
     * HTTP клиент специально для refresh операций (БЕЗ интерсептора авторизации)
     */
    private val refreshClient by lazy {
        HttpClient {
            install(HttpTimeout) {
                requestTimeoutMillis = TIMEOUT
                connectTimeoutMillis = CONNECT_TIMEOUT
                socketTimeoutMillis = TIMEOUT
            }

            install(ContentNegotiation) {
                json(
                    json = this@RefreshClientProvider.json,
                    contentType = ContentType.Application.Json
                )
            }

            installInternetConnectionChecker(isHasInternet)
        }
    }


    private val refreshApi by lazy {
        Ktorfit.Builder()
            .baseUrl(baseUrl)
            .httpClient(refreshClient)
            .build()
            // TODO: Замените на ваш метод создания API (например: .createApiAuthorization())
    }


    fun createRefreshTokenCall(): suspend (String) -> TokenPairModel? {
        return { refreshToken ->
           /* try {
                // TODO: Замените на ваш API метод refresh токена
                //  Пример: val response = refreshApi.refreshToken(RefreshTokenRequest(refreshToken))
                val response = TODO("Реализуйте вызов вашего API метода")

                if (response.statusCode == 200) {
                    val data = response.data
                    if (data?.accessToken != null && data.refreshToken != null) {
                        TokenPairModel(data.accessToken, data.refreshToken)
                    } else {
                        null
                    }
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }*/
            //TODO временная заглушка чтобы проект билдился
            null
        }
    }
}
