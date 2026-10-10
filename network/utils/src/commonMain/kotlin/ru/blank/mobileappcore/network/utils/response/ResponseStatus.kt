package ru.blank.mobileappcore.network.utils.response

/**
 * Иерархия классов для представления статусов сетевых запросов.
 *
 * @param T Тип успешных данных ответа (должен быть ненулевым)
 *
 * ## Иерархия состояний:
 * - [Success] - успешный ответ с данными
 * - [Error] - ошибка выполнения запроса
 *
 * ## Особенности:
 * - Поддерживает ковариантность out-параметра
 * - Предоставляет операцию [map] для трансформации данных
 * - Гарантирует обработку всех возможных состояний через when-выражение
 *
 * ## Пример использования:
 * ```kotlin
 * when (val response = fetchData()) {
 *     is ResponseStatus.Success -> processData(response.data)
 *     is ResponseStatus.Error -> showError(response.exception)
 * }
 * ```
 */
sealed class ResponseStatus<out T : Any> {

    /**
     * Успешное выполнение запроса.
     *
     * @property data Полезные данные ответа (может быть null)
     */
    data class Success<out T : Any>(val data: T?) : ResponseStatus<T>() {
        /**
         * Трансформирует успешные данные с помощью mapper-функции.
         *
         * @param mapper Функция преобразования T? в K?
         * @return Новый экземпляр Success с преобразованными данными
         */
        override fun <K : Any> map(mapper: (oldValue: T?) -> K?): ResponseStatus<K> {
            return Success(mapper.invoke(data))
        }
    }

    /**
     * Ошибка при выполнении запроса.
     *
     * @property exception Исключение, содержащее информацию об ошибке
     */
    data class Error<out T : Any>(val exception: Exception?) : ResponseStatus<T>() {
        /**
         * Сохраняет ошибку без изменений при трансформации.
         *
         * @return Новый экземпляр Error с тем же исключением
         */
        override fun <K : Any> map(mapper: (oldValue: T?) -> K?): ResponseStatus<K> {
            return Error(exception)
        }
    }

    /**
     * Абстрактная операция трансформации данных ответа.
     *
     * @param mapper Функция преобразования T? в K?
     * @return Новый ResponseStatus с преобразованными данными или той же ошибкой
     */
    abstract fun <K : Any> map(mapper: (oldValue: T?) -> K?): ResponseStatus<K>
}

