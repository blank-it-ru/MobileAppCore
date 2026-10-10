package ru.blank.mobileappcore.convention.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import ru.blank.mobileappcore.convention.config.Config
import ru.blank.mobileappcore.convention.config.configureComposeMultiplatform
import ru.blank.mobileappcore.convention.utils.plugins

/**
 * Плагин Gradle для настройки поддержки Compose Multiplatform в проекте.
 *
 * Этот плагин автоматически применяет необходимые плагины и настраивает
 * Kotlin Multiplatform для работы с Compose Multiplatform.
 *
 * Основные функции:
 * - Применяет три обязательных плагина:
 *   1. Плагин Kotlin Multiplatform (кастомная версия от Blank)
 *   2. Основной плагин Compose Multiplatform
 *   3. Плагин компилятора Compose
 * - Настраивает расширение Kotlin Multiplatform для работы с Compose
 */
class PluginComposeMultiplatform : Plugin<Project> {
    /**
     * Применяет плагин к целевому проекту.
     *
     * @param target Целевой проект Gradle, к которому применяется плагин
     */
    override fun apply(target: Project): Unit = with(target) {
        plugins {
            alias(Config.PluginValue.BLANK_KOTLIN_MULTIPLATFORM)
            alias(Config.PluginValue.JETBRAINS_COMPOSE_MULTIPLATFORM)
            alias(Config.PluginValue.JETBRAINS_COMPOSE_COMPILER)
        }
        extensions.configure<KotlinMultiplatformExtension>(::configureComposeMultiplatform)
    }
}

