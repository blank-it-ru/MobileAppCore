package ru.blank.mobileappcore.core.utils.errorMessageMapper

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import ru.blank.mobileappcore.core.entity.Entity
import ru.blank.mobileappcore.core.entity.ErrorCode
import mobileappcore.core.utils.errormessagemapper.generated.resources.Res
import mobileappcore.core.utils.errormessagemapper.generated.resources.error_empty_response
import mobileappcore.core.utils.errormessagemapper.generated.resources.error_no_internet_connection
import mobileappcore.core.utils.errormessagemapper.generated.resources.error_null_data
import mobileappcore.core.utils.errormessagemapper.generated.resources.error_unknown
import mobileappcore.core.utils.errormessagemapper.generated.resources.error_500
import mobileappcore.core.utils.errormessagemapper.generated.resources.error_404
import mobileappcore.core.utils.errormessagemapper.generated.resources.error_403
import mobileappcore.core.utils.errormessagemapper.generated.resources.error_401
import mobileappcore.core.utils.errormessagemapper.generated.resources.error_400

/**
 * Константа для сообщения об ошибке по умолчанию.
 * Используется, когда не удалось определить конкретное сообщение об ошибке.
 */
private const val UNKNOWN_ERROR_MESSAGE = "Произошла неизвестная ошибка"

/**
 * Маппинг кодов ошибок на строковые ресурсы.
 *
 * Сопоставляет каждый [ErrorCode] с соответствующим текстовым ресурсом:
 * - [ErrorCode.UNKNOWN] -> Общая ошибка
 * - [ErrorCode.NULL_DATA] -> Отсутствие данных
 * - [ErrorCode.CODE_500] -> Проблемы с интернет-соединением
 * - Остальные коды -> Общая ошибка (резервный вариант)
 */
private val errors: Map<ErrorCode, StringResource> = ErrorCode.entries.associateWith {
    when (it) {
        ErrorCode.CODE_500 -> Res.string.error_500
        ErrorCode.CODE_404 -> Res.string.error_404
        ErrorCode.CODE_403 -> Res.string.error_403
        ErrorCode.CODE_401 -> Res.string.error_401
        ErrorCode.CODE_400 -> Res.string.error_400
        ErrorCode.UNKNOWN -> Res.string.error_unknown
        ErrorCode.NULL_DATA -> Res.string.error_null_data
        ErrorCode.NO_INTERNET_CONNECTION -> Res.string.error_no_internet_connection
        ErrorCode.EMPTY_RESPONSE -> Res.string.error_empty_response
    }
}

/**
 * Расширение для получения UI-сообщения об ошибке.
 *
 * @return Текст ошибки в формате, готовом для отображения в UI
 *
 * ## Особенности:
 * - Для [Entity.Error.Type.Text] возвращает текст как есть
 * - Для [Entity.Error.Type.Code] ищет локализованный ресурс
 * - Использует резервный ресурс при отсутствии маппинга
 */
val Entity.Error.Type.ui
    @Composable get() = when (this) {
        is Entity.Error.Type.Text -> value
        is Entity.Error.Type.Code -> stringResource(errors[code] ?: Res.string.error_unknown)
    }

/**
 * Получает сообщение об ошибке в виде строки (suspend-версия).
 *
 * @return Локализованный текст ошибки
 *
 * ## Пример использования:
 * ```kotlin
 * try {
 *     fetchData()
 * } catch (e: Exception) {
 *     val message = errorType.message() // Получаем текст для логирования
 * }
 * ```
 */
suspend fun Entity.Error.Type.message(): String {
    return when (this) {
        is Entity.Error.Type.Text -> value
        is Entity.Error.Type.Code -> getString(errors[code] ?: Res.string.error_unknown)
    }
}

/**
 * Извлекает код ошибки из типа.
 *
 * @return [ErrorCode] для текстовых ошибок возвращает [ErrorCode.UNKNOWN]
 *
 * ## Пример использования:
 * ```kotlin
 * when (errorType.code()) {
 *     ErrorCode.CODE_404 -> showNotFound()
 *     else -> handleCommonError()
 * }
 * ```
 */
fun Entity.Error.Type.code(): ErrorCode {
    return when (this) {
        is Entity.Error.Type.Text -> ErrorCode.UNKNOWN
        is Entity.Error.Type.Code -> this.code
    }
}

/**
 * Получает локализованный текст для кода ошибки.
 *
 * @return Текст ошибки или [UNKNOWN_ERROR_MESSAGE], если ресурс не найден
 *
 * ## Особенности:
 * - Suspend-функция для корректной работы с ресурсами
 * - Использует предопределенный маппинг [errors]
 */
suspend fun ErrorCode.getText(): String {
    return errors[this]?.let { getString(it) } ?: UNKNOWN_ERROR_MESSAGE
}

