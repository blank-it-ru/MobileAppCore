plugins {
    alias(libs.plugins.blank.libs)
    alias(libs.plugins.blank.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Libs
            implementation(libs.kotlinx.datetime)
        }
    }
}

android.namespace = moduleNamespace.versions.core.utils.buildConfigChecker.get()

