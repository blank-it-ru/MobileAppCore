plugins {
    alias(libs.plugins.blank.libs)
    alias(libs.plugins.blank.kotlinMultiplatform)
    alias(libs.plugins.blank.composeMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Module
            implementation(projects.navigation.api)
            implementation(projects.feature.splashScreen.router)
            // Libs
            implementation(libs.koin.core)
        }
    }
}

android.namespace = moduleNamespace.versions.navigation.core.get()

