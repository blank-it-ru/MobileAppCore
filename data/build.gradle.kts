plugins {
    alias(libs.plugins.blank.libs)
    alias(libs.plugins.blank.kotlinMultiplatform)
    alias(libs.plugins.jetbrains.kotlinSerialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Module
            implementation(projects.core.entity)
            implementation(projects.core.utils.errorMessageMapper)
            implementation(projects.network.api)
            implementation(projects.network.provider)
            implementation(projects.network.utils)
            // Libs
            implementation(libs.kotlinx.coroutines)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.coroutines)
        }
    }
}

android.namespace = moduleNamespace.versions.data.get()

