plugins {
    alias(libs.plugins.blank.libs)
    alias(libs.plugins.blank.kotlinMultiplatform)
    alias(libs.plugins.jetbrains.kotlinSerialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Module
            implementation(projects.network.utils)
            implementation(projects.network.api)
            // Libs
            implementation(libs.ktorfit.lib)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
        }
    }
}

android.namespace = moduleNamespace.versions.network.clientProvider.get()

