plugins {
    alias(libs.plugins.blank.libs)
    alias(libs.plugins.blank.kotlinMultiplatform)
}

android.namespace = moduleNamespace.versions.core.entity.get()

