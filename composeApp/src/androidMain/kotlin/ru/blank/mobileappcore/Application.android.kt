package ru.blank.mobileappcore

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import ru.blank.mobileappcore.di.getCommonModules
import ru.blank.mobileappcore.di.getPlatformKoinModules

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(getCommonModules() + getPlatformKoinModules())
            androidLogger()
            androidContext(this@Application)
        }
    }
}

