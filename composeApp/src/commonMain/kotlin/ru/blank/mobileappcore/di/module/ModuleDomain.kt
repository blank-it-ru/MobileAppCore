package ru.blank.mobileappcore.di.module

import org.koin.dsl.module

/**
 * Объект для настройки dependency injection модуля доменной логики.
 *
 * Содержит фабричные методы для создания use case'ов и их регистрацию в DI-контейнере.
 *
 * ## Пример использования:
 *
 * 1. Создание метода для провайда зависимостей:
 * ```kotlin
 * private fun Scope.provideSomeUseCase(): SomeUseCase {
 *     return SomeUseCaseImpl(
 *         repository = get<SomeRepository>()
 *     )
 * }
 * ```
 *
 * 2. Регистрация в DI-контейнере:
 * ```kotlin
 * val module = module {
 *     factory<SomeUseCase> { provideSomeUseCase() }
 * }
 * ```
 *
 * ## Особенности:
 * - Все use case'ы регистрируются как factory (создаются новые экземпляры при каждом запросе)
 * - Для получения зависимостей используется Koin-метод `get()`
 * - Все методы провайда являются private и используются только внутри модуля
 */
internal object ModuleDomain {


    val module = module {

    }
}

