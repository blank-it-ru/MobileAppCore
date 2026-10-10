package ru.blank.mobileappcore.network.utils

/**
 * Базовое исключение для сетевых ошибок.
 *
 * @property message Сообщение об ошибке (может быть null)
 * @property cause Исходное исключение-причина
 *
 * Используется как родительский класс для всех кастомных сетевых исключений.
 */
class NetworkException(
    override val message: String?,
    override val cause: Throwable,
) : Exception(message, cause)

/**
 * Исключение с дополнительным кодом ошибки.
 *
 * @property code Код ошибки (опционально)
 * @property message Сообщение об ошибке (может быть null)
 * @property cause Исходное исключение-причина
 *
 * Пример использования:
 * ```kotlin
 * throw ExceptionWithCode(
 *     message = "Invalid token",
 *     code = "401",
 *     cause = originalException
 * )
 * ```
 */
class ExceptionWithCode(
    override val message: String?,
    val code: String?,
    override val cause: Throwable,
) : Exception(message, cause)

/**
 * Исключение при отсутствии сети (но с доступным интернетом).
 *
 * Может возникать при:
 * - Проблемах с DNS
 * - Недоступности конкретного сервера
 * - Ошибках маршрутизации
 *
 * @property message Сообщение об ошибке (может быть null)
 * @property cause Исходное исключение-причина
 */
class NoNetworkException(
    override val message: String?,
    override val cause: Throwable
) : Exception(message, cause)

/**
 * Исключение при полном отсутствии интернет-соединения.
 *
 * @property message Сообщение об ошибке (по умолчанию null)
 * @property cause Исходное исключение (по умолчанию Throwable)
 *
 * Пример обработки:
 * ```kotlin
 * try {
 *     fetchData()
 * } catch (e: NoInternetConnectionException) {
 *     showNoInternetWarning()
 * }
 * ```
 */
class NoInternetConnectionException(
    override val message: String? = null,
    override val cause: Throwable = Throwable(),
) : Exception(message, cause)

