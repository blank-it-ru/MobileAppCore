plugins {
    alias(libs.plugins.blank.libs)
    alias(libs.plugins.blank.kotlinMultiplatform)
    alias(libs.plugins.jetbrains.kotlinSerialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Module
            api(projects.navigation.api)
        }
    }
}

android.namespace = moduleNamespace.versions.feature.splashScreen.router.get()

