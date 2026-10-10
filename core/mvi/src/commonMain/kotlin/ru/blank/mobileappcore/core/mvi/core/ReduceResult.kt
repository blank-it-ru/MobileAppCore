package ru.blank.mobileappcore.core.mvi.core

import ru.blank.mobileappcore.core.mvi.label.BaseEffect
import ru.blank.mobileappcore.core.mvi.label.BaseEvent
import ru.blank.mobileappcore.core.mvi.label.BaseState

/**
 * Результат обработки интента, содержащий новое состояние, эффекты и события.
 *
 * @param S тип состояния, должен реализовывать [BaseState]
 * @param E тип событий, должен реализовывать [BaseEvent]
 * @param F тип эффектов, должен реализовывать [BaseEffect]
 * @property state новое состояние
 * @property effects набор эффектов для выполнения
 * @property events набор событий для отправки
 */
data class ReduceResult<S : BaseState, E : BaseEvent, F : BaseEffect>(
    val state: S,
    val effects: Set<F> = emptySet(),
    val events: Set<E> = emptySet()
)

