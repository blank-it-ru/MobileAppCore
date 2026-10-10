package ru.blank.mobileappcore.convention.config

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import ru.blank.mobileappcore.convention.utils.findLibByName

/**
 * Конфигурирует общие зависимости для Compose Multiplatform в Kotlin Multiplatform-модуле.
 *
 * Добавляет в `commonMain` следующие зависимости:
 * - `compose.foundation` — базовые UI-компоненты и layout-система;
 * - `compose.runtime` — основной runtime движок Compose.
 *
 * Зависимости добавляются как `api`, то есть будут транзитивно доступны во всех модулях,
 * которые зависят от этого.
 *
 * Имена зависимостей разрешаются через `findLibByName`, используя значения из [Config.LibName].
 *
 * @param extension Экземпляр [KotlinMultiplatformExtension], представляющий DSL для настройки KMP-модуля.
 */
internal fun Project.configureComposeMultiplatform(
    extension: KotlinMultiplatformExtension
) = extension.apply {
    sourceSets.apply {
        commonMain {
            dependencies {
                api(findLibByName(Config.LibName.COMPOSE_FOUNDATION))
                api(findLibByName(Config.LibName.COMPOSE_RUNTIME))
            }
        }
    }
}

