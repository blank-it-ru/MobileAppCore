package ru.blank.mobileappcore.convention.config

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import ru.blank.mobileappcore.convention.config.platform.configureAndroid
import ru.blank.mobileappcore.convention.config.platform.configureIos

/**
 * Конфигурирует базовую структуру Kotlin Multiplatform (KMP) модуля.
 *
 * Включает поддержку платформ:
 * - **Android** — через вызов [configureAndroid] и [androidTarget];
 * - **iOS** — через вызов [configureIos], предполагая поддержку всех нужных таргетов (iosX64, iosArm64 и т.д.).
 *
 * Также применяется шаблон иерархии исходных сетов ([applyDefaultHierarchyTemplate]),
 * чтобы упростить организацию `sourceSets` и переиспользование кода между платформами.
 *
 * @param extension Экземпляр [KotlinMultiplatformExtension], предоставляющий DSL для настройки multiplatform-модуля.
 */
internal fun Project.configureKotlinMultiplatform(
    extension: KotlinMultiplatformExtension
) = extension.apply {
    configureAndroid(this)
    configureIos(this)
    androidTarget()
    applyDefaultHierarchyTemplate()

    sourceSets.apply {
        commonMain {
            dependencies {
                if (this.project.path != Config.ModuleNamespace.LOGGER) {
                    implementation(project(Config.ModuleNamespace.LOGGER))
                }
            }
        }
    }
}

