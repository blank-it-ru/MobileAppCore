plugins {
    kotlin("jvm") version libs.versions.kotlin.get()
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        val prefix = "ru.blank.mobileappcore.convention.plugin."

        register("pluginAppModule") {
            id = "blank.app"
            implementationClass = "${prefix}PluginAppModule"
        }
        register("pluginLibModule") {
            id = "blank.libs"
            implementationClass = "${prefix}PluginLibModule"
        }
        register("pluginPresentationCore") {
            id = "blank.presentation.core"
            implementationClass = "${prefix}PluginPresentationCore"
        }
        register("pluginKotlinMultiplatform") {
            id = "blank.kotlin.multiplatform"
            implementationClass = "${prefix}PluginKotlinMultiplatform"
        }
        register("pluginComposeMultiplatform") {
            id = "blank.compose.multiplatform"
            implementationClass = "${prefix}PluginComposeMultiplatform"
        }
    }
}

