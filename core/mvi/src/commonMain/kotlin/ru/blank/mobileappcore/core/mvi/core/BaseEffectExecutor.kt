package ru.blank.mobileappcore.core.mvi.core

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.blank.mobileappcore.core.mvi.label.BaseEffect
import ru.blank.mobileappcore.core.mvi.label.BaseIntent

/**
 * Абстрактный базовый класс для исполнителей эффектов.
 * Позволяет управлять жизненным циклом эффектов (запуск, отмена).
 *
 * @param I тип интентов, должен реализовывать [BaseIntent]
 * @param F тип эффектов, должен реализовывать [BaseEffect]
 */
abstract class BaseEffectExecutor<I : BaseIntent, F : BaseEffect> {
    private val effectsHolder: MutableMap<String, Job?> = mutableMapOf()

    /**
     * Выполняет эффект с возможностью отмены по ключу.
     * Если эффект с таким ключом уже выполняется, новый не будет запущен.
     *
     * @param key уникальный ключ эффекта
     * @param execution лямбда с кодом для выполнения
     */
    protected suspend fun executeCancellable(
        key: String,
        execution: suspend () -> Unit
    ) = coroutineScope {
        if (effectsHolder.contains(key)) return@coroutineScope

        val effect = this.launch { execution() }
        effectsHolder[key] = effect
    }

    /**
     * Отменяет эффект по ключу.
     * @param key ключ эффекта для отмены
     */
    protected fun cancelEffect(key: String) {
        if (effectsHolder.contains(key)) {
            effectsHolder[key]?.cancel()
            effectsHolder.remove(key)
        }
    }

    /**
     * Отменяет все выполняющиеся эффекты.
     */
    protected fun cancelAllEffects() = effectsHolder.forEach { effect ->
        effect.value?.cancel()
    }.let {
        effectsHolder.clear()
    }

    /**
     * Абстрактный метод для выполнения эффекта.
     * Должен быть реализован в наследующих классах.
     *
     * @param eff эффект для выполнения
     * @param consumer функция для отправки интентов
     */
    abstract suspend fun execute(eff: F, consumer: (I) -> Unit)
}

