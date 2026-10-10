package ru.blank.mobileappcore.convention.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import ru.blank.mobileappcore.convention.config.Config
import ru.blank.mobileappcore.convention.config.configureKotlinMultiplatform
import ru.blank.mobileappcore.convention.utils.plugins

/**
 * Плагин Gradle для настройки поддержки Kotlin Multiplatform в проекте.
 *
 * Этот плагин выполняет базовую настройку модуля Kotlin Multiplatform,
 * применяя необходимые плагины и конфигурируя расширение для мультиплатформенного проекта.
 *
 * Основные функции:
 * - Применяет основной плагин Kotlin Multiplatform от JetBrains
 * - Настраивает расширение KotlinMultiplatformExtension с помощью внешней функции конфигурации
 */
class PluginKotlinMultiplatform : Plugin<Project> {
    /**
     * Применяет плагин к целевому проекту.
     *
     * @param target Целевой проект Gradle, к которому применяется плагин
     */
    override fun apply(target: Project): Unit = with(target) {
        plugins {
            alias(Config.PluginValue.JETBRAINS_KOTLIN_MULTIPLATFORM)
        }
        extensions.configure<KotlinMultiplatformExtension>(::configureKotlinMultiplatform)
    }
}

