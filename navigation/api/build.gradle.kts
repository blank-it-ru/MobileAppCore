plugins {
    alias(libs.plugins.blank.libs)
    alias(libs.plugins.blank.kotlinMultiplatform)
    alias(libs.plugins.blank.composeMultiplatform)
    alias(libs.plugins.jetbrains.kotlinSerialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Libs
            api(libs.compose.navigation)
            api(libs.kotlinx.serialization.json)
        }
    }
}

android.namespace = moduleNamespace.versions.navigation.api.get()

