package ru.blank.mobileappcore.di

import org.koin.core.module.Module

actual fun getPlatformKoinModules(): List<Module> {
    return listOf(
        ModuleApplication.module,
        ModuleNetwork.module,

    )
}

