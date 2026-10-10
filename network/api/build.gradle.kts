 plugins {
    alias(libs.plugins.blank.libs)
    alias(libs.plugins.blank.kotlinMultiplatform)
     alias(libs.plugins.jetbrains.kotlinSerialization)
     alias(libs.plugins.google.devtools.ksp)
     alias(libs.plugins.ktorfit)
 }

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Module
            implementation(projects.network.utils)
            // Libs
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktorfit.lib)
        }
    }
}

android.namespace = moduleNamespace.versions.network.api.get()

