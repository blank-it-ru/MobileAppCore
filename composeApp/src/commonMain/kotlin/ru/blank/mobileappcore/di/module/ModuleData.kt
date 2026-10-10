package ru.blank.mobileappcore.di.module

import com.russhwolf.settings.Settings
import org.koin.core.scope.Scope
import org.koin.dsl.module
import ru.blank.mobileappcore.data.TokenStorageImpl
import ru.blank.mobileappcore.network.provider.core.TokenStorage

/**
 * Объект для настройки dependency injection модуля данных.
 *
 * Содержит фабричные методы для создания репозиториев и их регистрацию в DI-контейнере.
 *
 * ## Пример использования:
 *
 * 1. Создание метода для провайда репозиториев:
 * ```kotlin
 * private fun Scope.provideSomeRepository(): SomeRepository {
 *     return SomeRepositoryImpl(
 *         dataSource = get<SomeDataSource>()
 *     )
 * }
 * ```
 *
 * 2. Регистрация в DI-контейнере:
 * ```kotlin
 * val module = module {
 *     single<SomeRepository> { provideSomeRepository() }
 * }
 * ```
 *
 * ## Особенности:
 * - Репозитории регистрируются как single (один экземпляр на все приложение)
 * - Для получения зависимостей используется Koin-метод `get()`
 * - Все методы провайда являются private и используются только внутри модуля
 */
internal object ModuleData {

    private fun Scope.provideTokenStorage(): TokenStorage {
        return TokenStorageImpl(
            settings = get<Settings>()
        )
    }


    val module = module {
        single<TokenStorage> { provideTokenStorage() }
    }
}
