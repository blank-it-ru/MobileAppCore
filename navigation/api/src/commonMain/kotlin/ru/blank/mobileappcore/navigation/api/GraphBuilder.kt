package ru.blank.mobileappcore.navigation.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * Интерфейс для построения графа навигации приложения.
 *
 * Определяет контракт для создания и настройки навигационного графа с поддержкой:
 * - Анимации переходов между экранами
 * - DI-интеграции через FeatureEntry
 * - Гибкой конфигурации NavHost
 *
 * ## Пример реализации:
 * ```kotlin
 * class MyGraphBuilder : GraphBuilder {
 *     @Composable
 *     override fun build(navController: NavHostController, isEnabledAnimation: Boolean) {
 *         // Настройка графа навигации
 *     }
 * }
 * ```
 *
 * ## Типичное использование:
 * 1. Создайте реализацию с указанием FeatureEntry
 * 2. Настройте NavHost с анимациями
 * 3. Зарегистрируйте все экраны приложения
 *
 * @see ru.blank.mobileappcore.navigation.api.entry.FeatureEntry Базовый интерфейс для модулей с экранами
 * @see NavHostController Контроллер навигации Compose
 */
interface GraphBuilder {
    /**
     * Строит граф навигации приложения.
     *
     * @param navController Контроллер для управления навигацией
     * @param isEnabledAnimation Флаг активности анимаций переходов
     *
     * ## Обязанности реализации:
     * - Регистрация всех экранов через NavHost
     * - Настройка анимаций переходов
     * - Определение стартового экрана
     * - Обработка deep links
     */
    @Composable
    fun build(navController: NavHostController, isEnabledAnimation: Boolean)
}

