package ru.blank.mobileappcore.convention.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import ru.blank.mobileappcore.convention.config.Config
import ru.blank.mobileappcore.convention.config.configurePresentationCore
import ru.blank.mobileappcore.convention.utils.plugins

/**
 * Плагин Gradle для настройки мультиплатформенного модуля presentation-core.
 *
 * Этот плагин предназначен для базовой конфигурации модулей, содержащих общую
 * presentation-логику и UI-компоненты с использованием Compose Multiplatform.
 *
 * Основные функции:
 * - Применяет кастомный плагин Compose Multiplatform (Blank версия)
 * - Настраивает Kotlin Multiplatform модуль через внешнюю функцию конфигурации
 * - Предполагается использование для gradle модулей с UI-компонентами
 */
class PluginPresentationCore : Plugin<Project> {
    /**
     * Применяет плагин к целевому проекту.
     *
     * @param target Целевой проект Gradle, к которому применяется плагин
     */
    override fun apply(target: Project): Unit = with(target) {
        plugins {
            alias(Config.PluginValue.BLANK_COMPOSE_MULTIPLATFORM)
        }
        extensions.configure<KotlinMultiplatformExtension>(::configurePresentationCore)
    }
}

