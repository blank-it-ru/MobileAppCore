package ru.blank.mobileappcore.di.module

import org.koin.core.scope.Scope
import org.koin.dsl.module
import ru.blank.mobileappcore.AppEnvironment
import ru.blank.mobileappcore.core.utils.buildConfigChecker.AppBuildConfig
import ru.blank.mobileappcore.network.provider.core.TokenStorage
import ru.blank.mobileappcore.network.provider.core.providers.NetworkProvider
import ru.blank.mobileappcore.network.provider.core.providers.RefreshClientProvider

/**
 * Объект для настройки dependency injection сетевого модуля.
 *
 * Содержит фабричные методы для создания сетевых зависимостей и их регистрацию в DI-контейнере.
 *
 * ## Пример использования:
 *
 * 1. Создание метода для провайда API:
 * ```kotlin
 * private fun Scope.provideSomeApi(): SomeApi {
 *     return get<NetworkProvider>().provideKtorfit(
 *         host = BASE_API_URL
 *     ).createSomeApi()
 * }
 * ```
 *
 * 2. Регистрация в DI-контейнере:
 * ```kotlin
 * val module = module {
 *     single<SomeApi> { provideSomeApi() }
 * }
 * ```
 *
 * ## Особенности:
 * - Сетевые провайдеры регистрируются как single (один экземпляр на все приложение)
 * - Все API клиенты создаются через общий [NetworkProvider]
 * - Базовый URL вынесен в константу класса
 * - Поддерживает как debug, так и release конфигурации
 */
object ModuleNetwork {
    private fun provideRefreshClientProvider(): RefreshClientProvider {
        return RefreshClientProvider(
            baseUrl = AppEnvironment.BASE_API_URL,
        )
    }

    private fun Scope.provideNetworkProvider(
        buildConfig: AppBuildConfig = get<AppBuildConfig>(),
    ): NetworkProvider {
        return NetworkProvider(
            isDebug = buildConfig.isDebug,
            tokenStorage = get<TokenStorage>(),
            refreshClientProvider = get<RefreshClientProvider>()
        )
    }

    val module = module {
        single<RefreshClientProvider> { provideRefreshClientProvider() }
        single<NetworkProvider> { provideNetworkProvider() }
    }
}

