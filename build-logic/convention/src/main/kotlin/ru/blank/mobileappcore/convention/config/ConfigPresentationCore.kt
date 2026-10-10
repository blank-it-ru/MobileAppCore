package ru.blank.mobileappcore.convention.config

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import ru.blank.mobileappcore.convention.utils.findLibByName

/**
 * Конфигурирует зависимости для слоя `presentation-core` в Kotlin Multiplatform проекте.
 *
 * Эта функция подключает основные библиотеки и фреймворки, необходимые для реализации
 * презентационного слоя, включая:
 *
 * - **Jetpack Compose Multiplatform**: `foundation`, `material3`, `ui`, `runtime`, `resources`
 * - **AndroidX Lifecycle**: `viewmodel`, `runtime` — для управления жизненным циклом и ViewModel
 * - **Koin**: `core`, `compose`, `compose-viewmodel` — для внедрения зависимостей в Compose
 *
 * Также здесь предусмотрено место для подключения внутренних модулей проекта
 *
 * Используется `api` для экспонирования библиотек наружу, чтобы они были доступны в других модулях,
 * зависящих от `presentation-core`.
 *
 * @param extension [KotlinMultiplatformExtension], в рамках которого производится настройка `sourceSets`.
 */
internal fun Project.configurePresentationCore(
    extension: KotlinMultiplatformExtension
) = extension.apply {
    sourceSets.apply {
        commonMain {
            dependencies {
                // Module
                implementation(project(Config.ModuleNamespace.CORE_MVI))
                // Libs
                api(findLibByName(Config.LibName.ANDROIDX_LIFECYCLE_VIEWMODEL))
                api(findLibByName(Config.LibName.ANDROIDX_LIFECYCLE_RUNTIME))
                api(findLibByName(Config.LibName.COMPOSE_FOUNDATION))
                api(findLibByName(Config.LibName.COMPOSE_MATERIAL_3))
                api(findLibByName(Config.LibName.COMPOSE_RESOURCES))
                api(findLibByName(Config.LibName.COMPOSE_RUNTIME))
                api(findLibByName(Config.LibName.COMPOSE_UI))
                api(findLibByName(Config.LibName.KOIN_CORE))
                api(findLibByName(Config.LibName.KOIN_COMPOSE))
                api(findLibByName(Config.LibName.KOIN_COMPOSE_VIEWMODEL))
            }
        }
    }
}

