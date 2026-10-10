package ru.blank.mobileappcore.convention.utils

import org.gradle.api.Project

/**
 * Добавляет зависимость в конфигурацию `implementation`.
 *
 * @param dependencyNotation Имя зависимости в формате, поддерживаемом [findLibByName]
 *
 * Использование:
 * ```kotlin
 * project.implementation("kotlinx.coroutines.core")
 * ```
 */
internal fun Project.implementation(dependencyNotation: String) {
    dependencies.add("implementation", findLibByName(dependencyNotation))
}

/**
 * Добавляет зависимость для обработки аннотаций через KSP (Kotlin Symbol Processing).
 *
 * @param dependencyNotation Имя процессора аннотаций в формате, поддерживаемом [findLibByName]
 *
 * Использование:
 * ```kotlin
 * project.ksp("com.google.devtools.ksp:symbol-processing-api")
 * ```
 */
internal fun Project.ksp(dependencyNotation: String) {
    dependencies.add("ksp", findLibByName(dependencyNotation))
}

/**
 * Добавляет зависимость только для debug-сборок.
 *
 * @param dependencyNotation Имя зависимости в формате, поддерживаемом [findLibByName]
 *
 * Типичное использование для debug-инструментов:
 * ```kotlin
 * project.debugImplementation("com.squareup.leakcanary:leakcanary-android")
 * ```
 */
internal fun Project.debugImplementation(dependencyNotation: String) {
    dependencies.add("debugImplementation", findLibByName(dependencyNotation))
}

