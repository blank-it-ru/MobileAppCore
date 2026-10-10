package ru.blank.mobileappcore.convention.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * Предоставляет доступ к каталогу зависимостей 'libs' из файла libs.versions.toml.
 *
 * Позволяет удобно получать зависимости в формате:
 * ```kotlin
 * val ktorVersion = project.libs.findVersion("ktor").get()
 * ```
 *
 * @return Объект VersionCatalog для работы с зависимостями
 * @throws org.gradle.api.UnknownDomainObjectException Если каталог 'libs' не найден
 */
val Project.libs
    get(): VersionCatalog = extensions
        .getByType<VersionCatalogsExtension>()
        .named("libs")

/**
 * Предоставляет доступ к каталогу пространств имен модулей 'moduleNamespace'.
 *
 * Используется для управления package-именами модулей в формате:
 * ```kotlin
 * val coreNamespace = project.moduleNamespace.findVersion("core").get()
 * ```
 *
 * @return Объект VersionCatalog для работы с пространствами имен
 * @throws org.gradle.api.UnknownDomainObjectException Если каталог 'moduleNamespace' не найден
 */
val Project.moduleNamespace
    get(): VersionCatalog = extensions
        .getByType<VersionCatalogsExtension>()
        .named("moduleNamespace")

