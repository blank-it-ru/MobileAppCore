package ru.blank.mobileappcore.network.utils.plugins

import io.ktor.client.HttpClientConfig
import io.ktor.client.request.HttpSendPipeline
import kotlinx.coroutines.delay
import ru.blank.mobileappcore.network.utils.NoInternetConnectionException

private const val DELAY_CONNECTION_CHECK = 500L
private const val HTTP_NETWORK_CHECKER_PLUGIN = "PluginNetworkHttpConnectionChecker"

/**
 * Устанавливает интерцептор для проверки интернет-соединения перед каждым HTTP-запросом.
 *
 * @param isHasInternet Текущее состояние соединения
 *
 * Поведение:
 * - При отсутствии соединения: [NoInternetConnectionException]
 * - При наличии соединения: запрос продолжается
 *
 * Пример:
 * ```kotlin
 * val client = HttpClient {
 *     installInternetConnectionChecker(checker)
 * }
 * ```
 */
fun HttpClientConfig<*>.installInternetConnectionChecker(isHasInternet: Boolean) {
    install(HTTP_NETWORK_CHECKER_PLUGIN) {
        sendPipeline.intercept(HttpSendPipeline.State) {
            if (!isHasInternet) {
                delay(DELAY_CONNECTION_CHECK)
                throw NoInternetConnectionException()
            }
            proceed()
        }
    }
}

