package ru.blank.mobileappcore.convention.utils

import org.gradle.api.Project

/**
 * Вспомогательный класс для применения плагинов Gradle в декларативном стиле.
 *
 * Позволяет собирать имена плагинов через DSL-подобный синтаксис перед их фактическим применением.
 *
 * Пример использования в Gradle-плагине:
 * ```kotlin
 * override fun apply(target: Project) {
 *     with(target) {
 *         plugins {
 *             alias(Config.PluginValue.ANDROID_APPLICATION)
 *             alias(Config.PluginValue.JETBRAINS_KOTLIN_MULTIPLATFORM)
 *         }
 *     }
 * }
 * ```
 *
 * Особенности реализации:
 * 1. Позволяет собирать плагины в декларативном стиле
 * 2. Применяет плагины последовательно в порядке их объявления
 * 3. Поддерживает как строковые идентификаторы, так и значения из конфигурации
 */
class PluginApplicator {
    /**
     * Список идентификаторов плагинов, которые будут применены.
     *
     * Хранит строковые идентификаторы плагинов в порядке их добавления.
     */
    val names = arrayListOf<String>()

    /**
     * Добавляет плагин в очередь на применение.
     *
     * @param id Идентификатор плагина (например, "com.android.application")
     * @receiver Текущий экземпляр PluginApplicator для поддержки DSL-синтаксиса
     */
    fun PluginApplicator.alias(id: String) {
        names.add(id)
    }
}

/**
 * Функция-расширение для применения плагинов через DSL-блок.
 *
 * @param applicator Лямбда с ресивером для настройки списка плагинов
 *
 * Пример использования:
 * ```kotlin
 * project.plugins {
 *     alias("com.android.application")
 *     alias("org.jetbrains.kotlin.multiplatform")
 * }
 * ```
 */
fun Project.plugins(applicator: PluginApplicator.() -> Unit) {
    val pluginConfig = PluginApplicator().apply(applicator)
    pluginConfig.names.forEach { pluginManager.apply(it) }
}

