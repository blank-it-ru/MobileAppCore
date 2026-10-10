package ru.blank.mobileappcore.convention.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider

/**
 * Найти номер версии по названию из каталога версий [libs].
 *
 * @param name - Название версии.
 * @return
 */
fun Project.findVersionByName(name: String): Int {
    return libs.findVersion(name).get().toString().toInt()
}

/**
 * Найти библиотеку по названию из каталога версий [libs].
 *
 * @param name - Название библиотеки.
 * @return
 */
fun Project.findLibByName(name: String): Provider<MinimalExternalModuleDependency> = runCatching {
    rootProject.libs.findLibrary(name).get()
}.onFailure { it.printStackTrace() }.getOrThrow()

