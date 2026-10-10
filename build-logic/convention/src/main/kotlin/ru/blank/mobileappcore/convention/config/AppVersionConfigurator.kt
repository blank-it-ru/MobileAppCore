package ru.blank.mobileappcore.convention.config

/**
 * Представляет версию приложения в виде компактного числового значения [code],
 * которое упаковывает major, minor и patch-версии в один целочисленный код.
 *
 * Структура кодирования:
 * - `major` — старшие 2 цифры (0–99)
 * - `minor` — средние 3 цифры (0–999)
 * - `patch` — младшие 4 цифры (0–9999)
 *
 * Это позволяет удобно хранить версию как одно число (например, для build.gradle),
 * при этом предоставляя доступ к каждой из составляющих через свойства [major], [minor], [patch].
 *
 * @property code Целочисленное представление версии. Используется для внутренних нужд и сортировки.
 * @property major Основная версия (0–99)
 * @property minor Минорная версия (0–999)
 * @property patch Патч-версия (0–9999)
 * @property name Строковое представление версии в формате "X.Y.Z"
 *
 * Пример:
 * ```
 * val version = AppVersionBuilder.build(1, 5, 42)
 * println(version.code) // 1050042
 * println(version.name) // "1.5.42"
 * ```
 */
@JvmInline
value class AppVersionBuilder(val code: Int) {
    val major: Int get() = code / 10_000_000
    val minor: Int get() = (code / 10_000) % 1_000
    val patch: Int get() = code % 10_000

    override fun toString(): String = "$major.$minor.$patch"

    val name
        get() = toString()

    companion object {
        /**
         * Создает экземпляр [AppVersionBuilder] из отдельных значений major, minor и patch.
         *
         * @param major Основная версия (0–99)
         * @param minor Минорная версия (0–999)
         * @param patch Патч-версия (0–9999)
         * @throws IllegalArgumentException если значения выходят за допустимые пределы
         */
        fun build(major: Int, minor: Int, patch: Int): AppVersionBuilder {
            require(major in 0..99) { "Major must be between 0 and 99" }
            require(minor in 0..999) { "Minor must be between 0 and 999" }
            require(patch in 0..9999) { "Patch must be between 0 and 9999" }

            val code = major * 10_000_000 + minor * 10_000 + patch
            return AppVersionBuilder(code)
        }
    }
}

