package ru.blank.mobileappcore.convention.config.platform

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import ru.blank.mobileappcore.convention.config.Config
import ru.blank.mobileappcore.convention.utils.findVersionByName

/**
 * Конфигурация плагина для Android платформы.
 *
 * @param extension
 */
internal fun Project.configureAndroid(extension: KotlinMultiplatformExtension) = extension.apply {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(findVersionByName(Config.JVM_TARGET).toString()))
        }
    }
}

