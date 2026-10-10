plugins {
    alias(libs.plugins.blank.libs)
    alias(libs.plugins.blank.kotlinMultiplatform)
    alias(libs.plugins.blank.composeMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Module
            implementation(projects.core.entity)
            // Libs
            implementation(libs.compose.components.resources)
        }
    }
}

android.namespace = moduleNamespace.versions.core.utils.errorMessageMapper.get()
compose.resources.publicResClass = true

