package ru.blank.mobileappcore.navigation.api

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

/**
 * Проверяет, находится ли текущий экран в состоянии RESUMED.
 *
 * @return `true` если экран в фокусе и активен, `false` в противном случае
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

/**
 * Выполняет навигацию только если экран в состоянии RESUMED.
 *
 * @param navigate Лямбда с действием навигации
 *
 * ## Пример использования:
 * ```kotlin
 * backStackEntry.withLifecycle { navController.navigate("route") }
 * ```
 */
fun NavBackStackEntry.withLifecycle(navigate: (NavBackStackEntry) -> Unit) {
    if (this.lifecycleIsResumed()) {
        navigate(this)
    }
}

/**
 * Инфиксная функция для безопасного возврата назад.
 *
 * @param backStackEntry Текущая запись в стеке навигации
 *
 * ## Пример использования:
 * ```kotlin
 * navController back currentBackStackEntry
 * ```
 */
infix fun NavHostController.back(backStackEntry: NavBackStackEntry) {
    backStackEntry.withLifecycle { popBackStack() }
}

/**
 * Сбрасывает граф навигации к стартовому экрану.
 *
 * @param inclusive Если `true`, очищает включая стартовый экран
 *
 * ## Особенности:
 * - Не выполняет сброс если текущий экран уже стартовый
 * - Проверяет состояние жизненного цикла
 */
fun NavHostController.resetGraph(inclusive: Boolean = false) {
    currentBackStackEntry?.withLifecycle {
        graph.startDestinationRoute?.let { startDestinationRoute ->
            if (currentDestination?.route != startDestinationRoute) {
                popBackStack(startDestinationRoute, inclusive = inclusive)
            }
        }
    }
}

/**
 * Выполняет навигацию с очисткой стека.
 *
 * @param route Сериализуемый маршрут назначения
 *
 * ## Особенности:
 * - Очищает весь стек до нового маршрута
 * - Гарантирует единственный экземпляр целевого экрана (launchSingleTop)
 * - Проверяет состояние жизненного цикла перед навигацией
 *
 * ## Пример использования:
 * ```kotlin
 * navController.navigateWithClearNavStack(MyScreenRoute(param = value))
 * ```
 */
fun <T : @Serializable Any> NavHostController.navigateWithClearNavStack(route: T) {
    currentBackStackEntry?.withLifecycle {
        navigate(route) {
            popUpTo(route) { inclusive = true }
            launchSingleTop = true
        }
    }
}

