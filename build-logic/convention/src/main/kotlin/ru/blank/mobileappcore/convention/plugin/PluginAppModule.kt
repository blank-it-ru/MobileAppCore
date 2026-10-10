package ru.blank.mobileappcore.convention.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import ru.blank.mobileappcore.convention.config.Config
import ru.blank.mobileappcore.convention.config.configureCommon
import ru.blank.mobileappcore.convention.utils.findVersionByName
import ru.blank.mobileappcore.convention.utils.libs
import ru.blank.mobileappcore.convention.utils.plugins
import java.io.File
import java.io.FileInputStream
import java.util.Properties

/**
 * Плагин Gradle для настройки модуля приложения.
 *
 * Этот плагин выполняет базовую настройку Android-приложения с поддержкой Kotlin Multiplatform,
 * включая конфигурацию модуля, flavors, типы сборки и подписание APK.
 *
 * Основные функции:
 * - Применяет необходимые плагины (Android Application и Kotlin Multiplatform)
 * - Настраивает исходные наборы (source sets) для мультиплатформенного проекта
 * - Добавляет product flavors (dev/prod) с разными конфигурациями
 * - Настраивает типы сборки (debug/release)
 * - Конфигурирует подписание APK для release-сборок
 *
 * @constructor Создает экземпляр плагина для настройки модуля приложения.
 */
class PluginAppModule : Plugin<Project> {
    /**
     * Применяет плагин к целевому проекту.
     *
     * @param target Целевой проект Gradle, к которому применяется плагин
     */
    override fun apply(target: Project) {
        with(target) {
            plugins {
                alias(Config.PluginValue.ANDROID_APPLICATION)
                alias(Config.PluginValue.JETBRAINS_KOTLIN_MULTIPLATFORM)
            }
            this.extensions.configure<BaseAppModuleExtension> {
                val android = extensions.getByName("android") as AppExtension
                sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
                sourceSets["main"].res.srcDirs("src/androidMain/res")
                sourceSets["main"].resources.srcDirs("src/commonMain/resources")

                configureCommon(this)
                applyFlavors(target)
                applyBuildTypes()
                configureSigningConfigs(android)
                packaging {
                    resources {
                        this.excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }
        }
    }

    /**
     * Настраивает product flavors для приложения.
     *
     * Добавляет два flavor:
     * - dev: для разработки, с суффиксом в applicationId
     * - prod: для production-сборок
     *
     * @param target Целевой проект Gradle
     * @receiver Расширение для конфигурации модуля приложения
     */
    private fun BaseAppModuleExtension.applyFlavors(target: Project) {
        flavorDimensions.add(Config.APP_NAME)
        productFlavors {
            defaultConfig {
                buildFeatures.buildConfig = true
                with(target) {
                    applicationId = libs.findVersion(Config.APP_ID).get().toString()
                    targetSdk = findVersionByName(Config.TARGET_SDK)
                    versionCode = Config.appVersion.code
                    versionName = Config.appVersion.name
                }
            }
            create(Config.FLAVOUR_DEV) {
                dimension = Config.APP_NAME
                applicationIdSuffix = Config.Dev.APPLICATION_ID_SUFFIX
            }
            create(Config.FLAVOUR_PROD) {
                dimension = Config.APP_NAME
            }
        }
    }

    /**
     * Настраивает типы сборки (build types) для приложения.
     *
     * Добавляет два типа сборки:
     * - debug: без минификации, с отладкой
     * - release: с минификацией и удалением неиспользуемых ресурсов
     *
     * @receiver Расширение для конфигурации модуля приложения
     */
    private fun BaseAppModuleExtension.applyBuildTypes() = this.buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    /**
     * Загружает свойства из файла local.properties.
     *
     * @param project Целевой проект Gradle
     * @return Загруженные свойства
     * @throws GradleException Если файл local.properties не найден
     */
    private fun loadLocalProperties(project: Project): Properties {
        val localPropertiesFile = File(project.rootDir, "local.properties")
        if (!localPropertiesFile.exists()) {
            throw GradleException("local.properties file not found")
        }

        val properties = Properties()
        properties.load(FileInputStream(localPropertiesFile))
        return properties
    }

    /**
     * Настраивает конфигурацию подписания APK для release-сборок.
     *
     * Загружает данные для подписания из local.properties и применяет их к release-сборке.
     *
     * @param android Расширение для конфигурации Android
     */
    private fun Project.configureSigningConfigs(android: AppExtension) {
        val properties = loadLocalProperties(this)

        android.signingConfigs.create(Config.BUILD_TYPE_RELEASE) {
            keyAlias = properties.getProperty("KEY_ALIAS")
            keyPassword = properties.getProperty("KEY_PASSWORD")
            storeFile = file(properties.getProperty("STORE_FILE"))
            storePassword = properties.getProperty("STORE_PASSWORD")
        }
        android.buildTypes.getByName(Config.BUILD_TYPE_RELEASE) {
            signingConfig = android.signingConfigs.getByName(Config.BUILD_TYPE_RELEASE)
        }
    }
}

