plugins {
    alias(libs.plugins.blank.libs)
    alias(libs.plugins.blank.kotlinMultiplatform)
    alias(libs.plugins.blank.composeMultiplatform)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            // Libs
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            // Module
            implementation(projects.core.logger)
            // Libs
            implementation(libs.compose.ui)
            implementation(libs.compose.foundation)
            implementation(libs.compose.runtime)
        }
        iosTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android.namespace = moduleNamespace.versions.core.webview.get()
