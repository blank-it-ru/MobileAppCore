package ru.blank.mobileappcore.convention.config

internal object Config {
    /** ID приложения (по умолчанию ru.blank.mobileappcore). */
    const val APP_ID = "applicationId"

    /** Название приложения. */
    const val APP_NAME = "MobileAppCore"

    /** Тип сборки : Релиз. */
    const val BUILD_TYPE_RELEASE = "release"

    /** Тип билда : Отладка. */
    const val BUILD_TYPE_DEBUG = "debug"

    /** Тип флейвора для продакшена. */
    const val FLAVOUR_PROD = "production"

    /** Тип флейвора для дебага. */
    const val FLAVOUR_DEV = "dev"

    /** Версии SDK, используемой при компиляции. */
    const val COMPILE_SDK = "android.compileSdk"

    /** Минимальная версия SDK, поддерживаемая приложением. */
    const val MIN_SDK = "android.minSdk"

    /** Целевая версия JVM (текущая версия "17"). */
    const val JVM_TARGET = "jvmTarget"

    /** Целевая версия SDK. */
    const val TARGET_SDK = "android.targetSdk"

    /**
     * Версия приложения, задаваемая через билд-систему.
     * Используется для установки versionCode и versionName.
     */
    val appVersion = AppVersionBuilder.build(0, 0, 1000)

    object Dev {
        const val APPLICATION_ID_SUFFIX = ".dev"
    }

    /**
     * Названия библиотек.
     * Из-за ограничений, мы не можем напрямую обращаться с toml и тянуть из них либы,
     * поэтому приходится тянуть через rootProject.
     * Значение константы из объекта [LibName] должно совпадать с названием библиотеки в libs.toml
     * файле, иначе будет ошибка.
     * */
    object LibName {
        const val ANDROIDX_LIFECYCLE_VIEWMODEL = "androidx.lifecycle.viewmodel"
        const val ANDROIDX_LIFECYCLE_RUNTIME = "androidx.lifecycle.runtimeCompose"
        const val COMPOSE_FOUNDATION = "compose.foundation"
        const val COMPOSE_MATERIAL_3 = "compose.material3"
        const val COMPOSE_RESOURCES = "compose.components.resources"
        const val COMPOSE_RUNTIME = "compose.runtime"
        const val COMPOSE_UI = "compose.ui"
        const val KOIN_CORE = "koin-core"
        const val KOIN_COMPOSE = "koin-compose"
        const val KOIN_COMPOSE_VIEWMODEL = "koin-compose-viewmodel"
    }

    object ModuleNamespace {
        const val CORE_MVI = ":core:mvi"
        const val LOGGER = ":core:logger"
    }

    object PluginValue {
        const val ANDROID_APPLICATION = "com.android.application"
        const val ANDROID_LIBRARY = "com.android.library"
        const val JETBRAINS_KOTLIN_MULTIPLATFORM = "org.jetbrains.kotlin.multiplatform"
        const val JETBRAINS_COMPOSE_MULTIPLATFORM = "org.jetbrains.compose"
        const val JETBRAINS_COMPOSE_COMPILER = "org.jetbrains.kotlin.plugin.compose"
        const val BLANK_KOTLIN_MULTIPLATFORM = "blank.kotlin.multiplatform"
        const val BLANK_COMPOSE_MULTIPLATFORM = "blank.compose.multiplatform"
    }
}

