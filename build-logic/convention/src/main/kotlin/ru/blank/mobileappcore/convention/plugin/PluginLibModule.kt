package ru.blank.mobileappcore.convention.plugin

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import ru.blank.mobileappcore.convention.config.Config
import ru.blank.mobileappcore.convention.config.configureCommon
import ru.blank.mobileappcore.convention.utils.plugins

/**
 * Плагин Gradle для настройки Android-библиотеки в проекте.
 *
 * Этот плагин выполняет базовую настройку модуля Android-библиотеки,
 * применяя необходимые плагины и делегируя общую конфигурацию внешней функции.
 *
 * Основные функции:
 * - Применяет плагин `android-library` для поддержки Android-библиотек
 * - Настраивает расширение LibraryExtension через общую функцию конфигурации
 */
class PluginLibModule : Plugin<Project> {
    /**
     * Применяет плагин к целевому проекту.
     *
     * @param target Целевой проект Gradle, к которому применяется плагин
     */
    override fun apply(target: Project) {
        with(target) {
            plugins {
                alias(Config.PluginValue.ANDROID_LIBRARY)
            }
            extensions.configure<LibraryExtension> {
                configureCommon(this)
            }
        }
    }
}

