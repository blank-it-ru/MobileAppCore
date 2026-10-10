package ru.blank.mobileappcore

import ru.blank.mobileappcore.core.utils.buildConfigChecker.AppBuildConfig

class AppBuildConfigCore : AppBuildConfig {
    override val isDebug: Boolean
        get() = BuildConfig.DEBUG
}

