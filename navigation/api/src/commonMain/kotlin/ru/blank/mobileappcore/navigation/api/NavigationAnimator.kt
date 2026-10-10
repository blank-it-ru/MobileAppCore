package ru.blank.mobileappcore.navigation.api

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * Composable-функция для создания и запоминания аниматора навигации.
 *
 * @param isEnabledAnimation Флаг включения/отключения анимаций
 * @return Экземпляр [NavigationAnimator] с настроенными анимациями
 *
 * ## Пример использования:
 * ```kotlin
 * val animator = rememberNavigationAnimator(isEnabledAnimation = true)
 *
 * NavHost(
 *     enterTransition = { animator.enterFromRight },
 *     exitTransition = { animator.exitToLeft }
 * )
 * ```
 */
@Composable
fun rememberNavigationAnimator(isEnabledAnimation: Boolean): NavigationAnimator = remember {
    NavigationAnimator(isEnabledAnimation)
}

/**
 * Класс для управления анимациями навигации в Compose.
 *
 * @property isEnabledAnimation Флаг активности анимаций (влияет на длительность)
 *
 * ## Особенности:
 * - Все анимации автоматически отключаются при isEnabledAnimation = false
 * - Поддерживает горизонтальные, вертикальные и fade-анимации
 * - Предоставляет готовые комбинации анимаций
 */
class NavigationAnimator(private val isEnabledAnimation: Boolean) {
    /**
     * Длительность анимации в миллисекундах.
     * Возвращает 0 если анимации отключены.
     */
    private val duration
        get() = if (isEnabledAnimation) 240 else 0

    /**
     * Комбинированная анимация входа справа:
     * - Слайд с правого края
     * - Постепенное появление
     */
    val enterFromRight = slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(durationMillis = duration)
    ) + fadeIn(animationSpec = tween(durationMillis = duration))

    /**
     * Комбинированная анимация выхода вправо:
     * - Слайд за правый край
     * - Постепенное исчезновение
     */
    val exitToRight = slideOutHorizontally(
        targetOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(durationMillis = duration)
    ) + fadeOut(animationSpec = tween(durationMillis = duration))

    /**
     * Комбинированная анимация входа снизу:
     * - Слайд с нижнего края
     * - Постепенное появление
     */
    val enterFromBottom = slideInVertically(
        initialOffsetY = { fullWidth -> fullWidth },
        animationSpec = tween(durationMillis = duration)
    ) + fadeIn(animationSpec = tween(durationMillis = duration))

    /**
     * Анимация выхода вниз:
     * - Слайд за нижний край
     */
    val exitToBottom = slideOutVertically(
        targetOffsetY = { fullWidth -> fullWidth },
        animationSpec = tween(durationMillis = duration)
    )

    /**
     * Комбинированная анимация входа слева:
     * - Слайд с левого края
     * - Постепенное появление
     */
    val enterFromLeft = slideInHorizontally(
        initialOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(durationMillis = duration)
    ) + fadeIn(animationSpec = tween(durationMillis = duration))

    /**
     * Комбинированная анимация выхода влево:
     * - Слайд за левый край
     * - Постепенное исчезновение
     */
    val exitToLeft = slideOutHorizontally(
        targetOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(durationMillis = duration)
    ) + fadeOut(animationSpec = tween(durationMillis = duration))

    /**
     * Задержанное исчезновение:
     * - Начинается с задержкой в половину длительности
     * - Плавное исчезновение
     */
    val delayedFadeOut = fadeOut(
        animationSpec = tween(
            durationMillis = duration,
            delayMillis = duration / 2
        )
    )

    /**
     * Простое появление (fade-in)
     */
    val fadeInAnimation = fadeIn(
        animationSpec = tween(durationMillis = duration, easing = LinearEasing)
    )

    /**
     * Простое исчезновение (fade-out)
     */
    val fadeOutAnimation = fadeOut(
        animationSpec = tween(durationMillis = duration, easing = LinearEasing)
    )

    /**
     * Специальная анимация возврата (пружинная):
     * - Вертикальный слайд с эффектом пружины
     * - Постепенное появление
     */
    val popEnterAnimation = slideInVertically(
        initialOffsetY = { it },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessLow
        )
    ) + fadeIn()

    /**
     * Специальная анимация возврата (пружинная):
     * - Вертикальный слайд с эффектом пружины
     * - Постепенное исчезновение
     */
    val popExitAnimation = slideOutVertically(
        targetOffsetY = { it },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        )
    ) + fadeOut()
}

