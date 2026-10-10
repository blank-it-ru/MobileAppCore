plugins {
    alias(libs.plugins.blank.app)
    alias(libs.plugins.blank.kotlinMultiplatform)
    alias(libs.plugins.blank.composeMultiplatform)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            // Libs
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            // Module
            implementation(projects.core.utils.buildConfigChecker)
            implementation(projects.core.environment)
            implementation(projects.data)
            implementation(projects.navigation.core)
            implementation(projects.network.api)
            implementation(projects.network.provider)
            implementation(projects.network.utils)
            implementation(projects.feature.splashScreen.presentation)
            // Libs
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.components.uiToolingPreview)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.multiplatform.settings)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android.namespace = moduleNamespace.versions.app.get()

dependencies {
    debugImplementation(compose.uiTooling)
}

