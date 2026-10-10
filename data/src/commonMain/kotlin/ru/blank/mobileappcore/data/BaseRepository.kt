package ru.blank.mobileappcore.data

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json
import ru.blank.mobileappcore.core.entity.Entity
import ru.blank.mobileappcore.core.entity.ErrorCode
import ru.blank.mobileappcore.core.utils.errorMessageMapper.getText
import ru.blank.mobileappcore.network.utils.NetworkException
import ru.blank.mobileappcore.network.utils.NoInternetConnectionException
import ru.blank.mobileappcore.network.utils.NoNetworkException
import ru.blank.mobileappcore.network.utils.response.NetworkResponse
import ru.blank.mobileappcore.network.utils.response.ResponseStatus
import ru.blank.mobileappcore.network.utils.response.Status

/**
 * Абстрактный базовый репозиторий для безопасной работы с сетевыми запросами.
 *
 * Предоставляет стандартизированные методы для обработки API-запросов с:
 * - Автоматической обработкой ошибок
 * - Парсингом ошибок сервера
 * - Трансформацией результатов
 * - Поддержкой различных типов ответов
 *
 * @property tag Тег для логирования (по умолчанию "Repository")
 */
internal abstract class BaseRepository(
    protected val tag: String? = TAG,
) {
    protected val json by lazy { Json { ignoreUnknownKeys = true } }

    /**
     * Безопасно выполняет API-запрос без парсинга конкретного тела ответа.
     *
     * @param call Функция, выполняющая HTTP-запрос
     * @return [ResponseStatus] с результатом или ошибкой
     */
    protected suspend inline fun <reified K : Any> safeApiSuspendResultNoResponse(
        call: () -> HttpResponse?
    ): ResponseStatus<K> {
        return try {
            val response = call.invoke()
            when {
                response == null -> {
                    ResponseStatus.Error(
                        NetworkException(
                            message = ErrorCode.EMPTY_RESPONSE.getText(),
                            cause = Throwable(tag)
                        )
                    )
                }

                response.status.isSuccess() -> ResponseStatus.Success(response.body<K>())
                else -> handleErrorResponse<K>(response)
            }
        } catch (e: Exception) {
            handleNetworkException(e)
        }
    }

    /**
     * Безопасно выполняет API-запрос с парсингом стандартного сетевого ответа.
     *
     * @param call Функция, возвращающая [NetworkResponse]
     * @return [ResponseStatus] с данными или ошибкой
     */
    protected suspend fun <K : Any> safeApiSuspendResult(
        call: suspend () -> NetworkResponse<K>?
    ): ResponseStatus<K> {
        return try {
            val response = call.invoke()
            when (Status.fromStatusCode(response?.statusCode ?: DEFAULT_STATUS_CODE)) {
                Status.SUCCESS -> ResponseStatus.Success(response?.data)
                else -> {
                    ResponseStatus.Error(
                        NetworkException(
                            message = response?.message,
                            cause = Throwable(tag)
                        )
                    )
                }
            }
        } catch (e: Exception) {
            handleNetworkException(e)
        }
    }

    /**
     * Трансформирует [ResponseStatus] в [Entity] с обработкой ошибок.
     *
     * @param onSuccess Функция для обработки успешного результата
     * @param onError Функция для обработки ошибки (может вернуть ErrorCode или Exception)
     * @return [Entity] с преобразованными данными или ошибкой
     */
    protected suspend fun <T : Any, R : Any> ResponseStatus<T>.map(
        onSuccess: suspend (T) -> R,
        onError: suspend (Exception?) -> Any?
    ): Entity<R> {
        return when (this) {
            is ResponseStatus.Success -> {
                data?.let { data ->
                    Entity.Success(onSuccess(data))
                } ?: Entity.Error(handleErrorResult(onError(null)))
            }

            is ResponseStatus.Error -> Entity.Error(handleErrorResult(onError(exception)))
        }
    }

    /**
     * Обрабатывает HTTP-ответ с ошибкой.
     */
    private suspend fun <T : Any> handleErrorResponse(
        response: HttpResponse
    ): ResponseStatus.Error<T> {
        val errorBody = response.bodyAsText()
        val errorMessage = parseBackendErrorMessage(errorBody)
        return ResponseStatus.Error(
            NetworkException(
                message = errorMessage,
                cause = Throwable(tag)
            )
        )
    }

    /**
     * Обрабатывает исключения сети.
     */
    private suspend fun <T : Any> handleNetworkException(e: Exception): ResponseStatus.Error<T> {
        return when (e) {
            is ClientRequestException -> {
                ResponseStatus.Error(NetworkException(e.response.bodyAsText(), Throwable(tag)))
            }

            is ServerResponseException -> {
                ResponseStatus.Error(NetworkException(e.message, Throwable(tag)))
            }

            is NoInternetConnectionException -> {
                ResponseStatus.Error(
                    NoInternetConnectionException(ErrorCode.CODE_500.getText(), Throwable(tag))
                )
            }

            else -> ResponseStatus.Error(NoNetworkException(e.message, Throwable(tag)))
        }
    }

    /**
     * Преобразует результат обработки ошибки в стандартный тип.
     */
    private fun handleErrorResult(result: Any?): Entity.Error.Type {
        return when (result) {
            is ErrorCode -> Entity.Error.Type.Code(result)
            is Exception -> {
                result.message?.let { Entity.Error.Type.Text(it) }
                    ?: Entity.Error.Type.Code(ErrorCode.NULL_DATA)
            }

            null -> Entity.Error.Type.Code(ErrorCode.NULL_DATA)
            else -> Entity.Error.Type.Code(ErrorCode.UNKNOWN)
        }
    }

    /**
     * Парсит сообщение об ошибке из тела ответа сервера.
     */
    private suspend fun parseBackendErrorMessage(errorBody: String?): String {
        if (errorBody.isNullOrBlank()) return ErrorCode.UNKNOWN.getText()
        return runCatching {
            json.decodeFromString<NetworkResponse.Error>(errorBody).message
                ?: ErrorCode.UNKNOWN.getText()
        }.getOrElse { ErrorCode.UNKNOWN.getText() }
    }

    companion object {
        private const val TAG = "Repository"
        private const val DEFAULT_STATUS_CODE = 0
    }
}

