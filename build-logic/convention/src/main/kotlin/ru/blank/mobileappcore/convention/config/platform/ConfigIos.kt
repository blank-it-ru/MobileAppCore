package ru.blank.mobileappcore.convention.config.platform

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Конфигурация плагина для IOS платформы.
 *
 * @param extension
 */
internal fun configureIos(extension: KotlinMultiplatformExtension) = extension.apply {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
}

