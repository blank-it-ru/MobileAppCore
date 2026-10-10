buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.gradlePlugin)
    }
}
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.google.devtools.ksp) apply false
    alias(libs.plugins.jetbrains.composeMultiplatform) apply false
    alias(libs.plugins.jetbrains.composeCompiler) apply false
    alias(libs.plugins.jetbrains.kotlinMultiplatform) apply false
    alias(libs.plugins.ktorfit) apply false
    alias(libs.plugins.blank.app) apply false
    alias(libs.plugins.blank.libs) apply false
    alias(libs.plugins.blank.kotlinMultiplatform) apply false
    alias(libs.plugins.blank.composeMultiplatform) apply false
    alias(libs.plugins.blank.presentation.core) apply false
}

