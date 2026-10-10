package ru.blank.mobileappcore.core.entity

/**
 * Иерархия классов для унифицированного представления сущностей с состоянием.
 *
 * Позволяет обрабатывать результаты операций в стиле Either:
 * - Успешный результат с данными ([Success])
 * - Ошибка с детализацией ([Error])
 *
 * ## Пример использования:
 * ```kotlin
 * fun fetchData(): Entity<Data> {
 *     return try {
 *         Entity.Success(api.getData())
 *     } catch (e: Exception) {
 *         Entity.Error(Entity.Error.Type.Text("Ошибка загрузки"))
 *     }
 * }
 *
 * when (val result = fetchData()) {
 *     is Entity.Success -> showData(result.data)
 *     is Entity.Error -> showError(result.error)
 * }
 * ```
 *
 * @param T Тип успешных данных (контравариантный)
 */
sealed class Entity<out T : Any> {
    /**
     * Успешный результат операции.
     *
     * @property data Полезные данные результата
     */
    data class Success<out T : Any>(
        val data: T,
    ) : Entity<T>()

    /**
     * Результат с ошибкой.
     *
     * Содержит детализированную информацию об ошибке через вложенный [Type].
     *
     * @property error Тип и детали ошибки
     */
    data class Error<out T : Any>(
        val error: Type,
    ) : Entity<T>() {
        /**
         * Иерархия типов ошибок.
         *
         * Поддерживает:
         * - Текстовые ошибки ([Text])
         * - Ошибки с кодом ([Code])
         */
        sealed class Type {
            /**
             * Текстовая ошибка.
             *
             * @property value Человеко-читаемое сообщение
             */
            data class Text(val value: String) : Type()

            /**
             * Ошибка с кодом.
             *
             * @property code Перечисление или класс с кодами ошибок
             */
            data class Code(val code: ErrorCode) : Type()
        }
    }
}

