plugins {
    alias(libs.plugins.blank.libs)
    alias(libs.plugins.blank.kotlinMultiplatform)
    alias(libs.plugins.blank.composeMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Libs
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.kotlinx.coroutines)
        }
    }
}

android.namespace = moduleNamespace.versions.core.mvi.get()

