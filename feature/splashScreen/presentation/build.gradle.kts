plugins {
    alias(libs.plugins.blank.libs)
    alias(libs.plugins.blank.presentation.core)
    alias(libs.plugins.jetbrains.kotlinSerialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Module
            api(projects.feature.splashScreen.router)
        }
    }
}

android.namespace = moduleNamespace.versions.feature.splashScreen.presentation.get()

