import java.util.Properties

rootProject.name = "MobileAppCore"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        provideBlankLibraries {
            listOf(

            )
        }
        versionCatalogs {
            create("moduleNamespace") {
                from(files("gradle/moduleNamespace.versions.toml"))
            }
        }
    }
}

/**
 * Подключает Maven-репозитории GitLab библиотек Blank, указанных в виде пар (ID проекта, имя токена).
 *
 * Для каждого проекта будет добавлен Maven-репозиторий по адресу:
 * `https://gitlab.com/api/v4/projects/{projectId}/packages/maven`
 *
 * - **Project ID** должен быть строкой длиной ровно 8 символов. Его можно найти в GitLab в разделе
 *   `Settings → General` соответствующего репозитория (поле **Project ID**).
 * - **Token name** — это имя свойства, в котором в `local.properties` хранится GitLab Private Token
 *   с правами доступа к Maven Registry. Значение токена подгружается из `local.properties`.
 *
 * Пример использования:
 * ```kotlin
 * provideBlankLibraries {
 *     listOf(
 *         "12345678" to "GITLAB_LIB_TOKEN"
 *     )
 * }
 * ```
 *
 * @param libsProvider функция, возвращающая список пар (ID проекта GitLab, имя свойства с токеном).
 * @throws IllegalArgumentException если ID проекта некорректной длины.
 * @throws GradleException если не найдено указанное свойство токена в `local.properties`.
 */
private fun RepositoryHandler.provideBlankLibraries(libsProvider: () -> List<Pair<String, String>>) {
    val libs = libsProvider()

    if (libs.isNotEmpty()) {
        libs.forEach { lib ->
            require(lib.first.isNotEmpty() && lib.first.length == 8) {
                "Некорректный GitLab Project ID: '${lib.first}'. Должен быть строкой длиной ровно 8 символов."
            }
            maven {
                url = uri("https://gitlab.com/api/v4/projects/${lib.first}/packages/maven")
                name = "GitLab"
                credentials(HttpHeaderCredentials::class) {
                    name = "Private-Token"
                    value = loadLocalProperties()?.getProperty(lib.second) ?: error(
                        "В local.properties отсутствует свойство '${lib.second}'"
                    )
                }
                authentication {
                    create<HttpHeaderAuthentication>("header")
                }
            }
        }
    }
}

/**
 * Загружает свойства из файла local.properties в корне проекта.
 * Файл должен существовать и содержать необходимые свойства, такие как GITLAB_PRIVATE_TOKEN.
 *
 * @return Объект [Properties] с загруженными свойствами из файла.
 * @throws GradleException Если файл local.properties не найден.
 */
private fun loadLocalProperties(): Properties? {
    val localPropertiesFile = File(rootProject.projectDir, "local.properties")
    if (!localPropertiesFile.exists()) {
        return null
    }
    return Properties().apply {
        localPropertiesFile.inputStream().use { load(it) }
    }
}

include(":composeApp")
include(":data")
include(":domain")

include(":core:entity")
include(":core:mvi")
include(":core:logger")
include(":core:environment")
include(":core:webview")

include(":core:utils:buildConfigChecker")
include(":core:utils:errorMessageMapper")

include(":navigation:api")
include(":navigation:core")

include(":network:api")
include(":network:provider")
include(":network:utils")

include(":feature:splashScreen:presentation")
include(":feature:splashScreen:router")

