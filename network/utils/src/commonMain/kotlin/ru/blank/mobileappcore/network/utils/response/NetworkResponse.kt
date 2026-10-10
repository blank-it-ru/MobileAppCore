package ru.blank.mobileappcore.network.utils.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Универсальный контейнер для сетевых ответов API.
 *
 * @param T Тип полезной нагрузки данных
 * @property data Основные данные ответа (может быть null)
 * @property statusCode HTTP статус-код ответа
 * @property message Сообщение от сервера (опционально)
 * @property code Кастомный код ошибки/статуса (опционально)
 *
 * ## Пример использования:
 * ```kotlin
 * @GET("user/profile")
 * suspend fun getProfile(): NetworkResponse<ProfileDto>
 *
 * // Обработка ответа
 * when (response.statusCode) {
 *     200 -> processData(response.data)
 *     else -> showError(response.message)
 * }
 * ```
 */
@Serializable
data class NetworkResponse<T>(
    @SerialName("data") val data: T? = null,
    @SerialName("statusCode") val statusCode: Int = 0,
    @SerialName("message") val message: String? = null,
    @SerialName("code") val code: Int = 0
) {
    @Serializable
    data class Error(
        @SerialName("message") val message: String? = null,
        @SerialName("error") val error: String? = null,
        @SerialName("statusCode") val statusCode: Int? = null
    )
}

/**
 * Статус обработки сетевого запроса.
 *
 * Сериализуемый enum для унифицированного представления статусов.
 *
 * ## Соответствие HTTP кодам:
 * - SUCCESS: 200-299
 * - ERROR: 400-499 (клиентские ошибки)
 * - FAILED: 500-599 (серверные ошибки)
 *
 * ## Пример использования:
 * ```kotlin
 * val status = Status.fromStatusCode(response.statusCode)
 * when (status) {
 *     Status.SUCCESS -> ...
 *     Status.ERROR -> ...
 *     Status.FAILED -> ...
 * }
 * ```
 */
@Serializable
enum class Status {
    /**
     * Успешная обработка запроса.
     */
    @SerialName("success")
    SUCCESS,

    /**
     * Ошибка на стороне клиента.
     */
    @SerialName("error")
    ERROR,

    /**
     * Ошибка на стороне сервера.
     */
    @SerialName("failed")
    FAILED;

    companion object {
        /**
         * Конвертирует HTTP статус-код в enum Status.
         *
         * @param code HTTP статус-код
         * @return Соответствующий статус обработки
         */
        fun fromStatusCode(code: Int): Status {
            return when (code) {
                in 200..299 -> SUCCESS
                in 400..499 -> ERROR
                in 500..599 -> FAILED
                else -> ERROR
            }
        }
    }
}

