package ru.blank.mobileappcore

import ru.blank.mobileappcore.core.utils.buildConfigChecker.AppBuildConfig
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform

class AppBuildConfigCore : AppBuildConfig {
    @OptIn(ExperimentalNativeApi::class)
    override val isDebug: Boolean
        get() = Platform.isDebugBinary
}

