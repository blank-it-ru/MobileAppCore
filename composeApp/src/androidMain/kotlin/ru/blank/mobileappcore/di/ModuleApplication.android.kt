package ru.blank.mobileappcore.di

import org.koin.dsl.module
import ru.blank.mobileappcore.AppBuildConfigCore
import ru.blank.mobileappcore.core.utils.buildConfigChecker.AppBuildConfig

/**
 * Объект для настройки dependency injection основного модуля приложения.
 *
 * Содержит регистрацию глобальных зависимостей, которые используются на уровне всего приложения.
 *
 * ## Пример добавления зависимостей:
 *
 * 1. Регистрация сервиса:
 * ```kotlin
 * single<AnalyticsService> { FirebaseAnalyticsService(get()) }
 * ```
 *
 * 2. Регистрация менеджера:
 * ```kotlin
 * single<SessionManager> { SessionManagerImpl(get()) }
 * ```
 *
 * ## Особенности:
 * - Все зависимости регистрируются как single (один экземпляр на все приложение)
 * - Содержит только глобальные зависимости уровня приложения
 * - Может включать:
 *   - Сервисы аналитики
 *   - Менеджеры сессий
 *   - Глобальные конфигурации
 *   - Общие утилиты
 */
object ModuleApplication {
    private fun provideAppBuildConfigChecker(): AppBuildConfig {
        return AppBuildConfigCore()
    }

    val module = module {
        factory<AppBuildConfig> { provideAppBuildConfigChecker() }
    }
}

