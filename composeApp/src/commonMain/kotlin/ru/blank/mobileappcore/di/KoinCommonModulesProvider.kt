package ru.blank.mobileappcore.di

import org.koin.core.module.Module
import ru.blank.mobileappcore.di.module.ModuleDomain
import ru.blank.mobileappcore.di.module.ModuleData
import ru.blank.mobileappcore.di.module.ModuleNetwork
import ru.blank.mobileappcore.feature.splashScreen.presentation.di.ModulePresentationSplashScreen
import ru.blank.mobileappcore.navigation.core.di.ModuleNavigation

/**
 * Собирает список общих Koin-модулей для всех платформ.
 *
 * Возвращает объединенный список модулей:
 * - [ModuleDomain] - доменная логика
 * - [ModuleData] - работа с данными
 * - [ModuleNavigation] - навигация
 * - [ModuleNetwork] - сетевое взаимодействие
 *  и остальные модули общие платформонезависимые модули.
 *
 * @return Неизменяемый список общих модулей DI
 */
internal fun getCommonModules(): List<Module> = listOf(
    ModuleDomain.module,
    ModuleData.module,
    ModuleNavigation.module,
    ModuleNetwork.module,
    ModulePresentationSplashScreen.module,
)

