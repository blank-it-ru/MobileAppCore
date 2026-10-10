package ru.blank.mobileappcore.convention.config

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import ru.blank.mobileappcore.convention.utils.findVersionByName

/**
 * Общая конфигурация Android-модуля (application или library).
 *
 * Настраивает ключевые параметры SDK и Java-совместимости:
 * - `compileSdk` — версия SDK, используемая для компиляции;
 * - `minSdk` — минимальная поддерживаемая версия Android;
 * - `compileOptions` — версия Java (JVM target), используемая при компиляции.
 *
 * Все значения читаются из конфигурационного объекта `Config` с помощью утилиты `findVersionByName`.
 *
 * @param commonExtension Экземпляр [CommonExtension], предоставляющий DSL для конфигурации Android-модуля.
 */
internal fun Project.configureCommon(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    commonExtension.apply {
        compileSdk = findVersionByName(Config.COMPILE_SDK)
        defaultConfig {
            minSdk = findVersionByName(Config.MIN_SDK)
        }
        compileOptions {
            val javaVersion = findVersionByName(Config.JVM_TARGET)
            sourceCompatibility = JavaVersion.toVersion(javaVersion)
            targetCompatibility = JavaVersion.toVersion(javaVersion)
        }
    }
}

