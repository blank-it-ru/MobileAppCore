package ru.blank.mobileappcore

import org.koin.core.context.startKoin
import ru.blank.mobileappcore.di.getCommonModules
import ru.blank.mobileappcore.di.getPlatformKoinModules

fun initKoin() {
    startKoin {
        modules(getCommonModules() + getPlatformKoinModules())
    }
}

