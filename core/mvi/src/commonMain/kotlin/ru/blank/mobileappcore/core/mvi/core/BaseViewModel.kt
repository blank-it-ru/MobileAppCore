package ru.blank.mobileappcore.core.mvi.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import ru.blank.mobileappcore.core.mvi.label.BaseEffect
import ru.blank.mobileappcore.core.mvi.label.BaseEvent
import ru.blank.mobileappcore.core.mvi.label.BaseIntent
import ru.blank.mobileappcore.core.mvi.label.BaseState

/**
 * Абстрактный базовый ViewModel.
 *
 * @param S тип состояния экрана, должен реализовывать [BaseState]
 * @param I тип интентов, должен реализовывать [BaseIntent]
 * @param E тип событий, должен реализовывать [BaseEvent]
 * @param F тип эффектов, должен реализовывать [BaseEffect]
 * @param initialState начальное состояние
 * @param initialEffects начальные эффекты, которые будут выполнены при инициализации
 * @param initialEvents начальные события, которые будут отправлены при инициализации
 * @param effectExecutors список исполнителей эффектов
 */
abstract class BaseViewModel<S : BaseState, I : BaseIntent, E : BaseEvent, F : BaseEffect>(
    initialState: S,
    initialEffects: Set<F> = emptySet(),
    initialEvents: Set<E> = emptySet(),
    private val effectExecutors: List<BaseEffectExecutor<I, F>> = emptyList(),
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)

    /**
     * StateFlow с текущим состоянием экрана.
     * Подписывайтесь на этот Flow для получения обновлений состояния.
     */
    val state: StateFlow<S> = _state.asStateFlow()

    private val _intents = MutableSharedFlow<I>()

    private val _events = Channel<E>()

    /**
     * Flow для одноразовых событий (например, ошибок, уведомлений).
     * Подписывайтесь на этот Flow для обработки событий, которые должны быть обработаны один раз.
     */
    val events = _events.receiveAsFlow()

    init {
        reduce(ReduceResult(initialState, initialEffects, initialEvents))
        subscribeToIntents()
    }

    /**
     * Подписка на поток интентов и обработка их в [handleIntent].
     * Автоматически запускается при инициализации ViewModel.
     */
    private fun subscribeToIntents() {
        viewModelScope.launch {
            _intents.collect { intent ->
                reduce(handleIntent(intent, _state.value))
            }
        }
    }

    /**
     * Выполняет набор побочных эффектов.
     * @param effects набор эффектов для выполнения
     */
    private fun executeEffects(effects: Set<F>) {
        if (effects.isNotEmpty()) {
            effects.forEach { eff ->
                effectExecutors.forEach { executor ->
                    viewModelScope.launch {
                        yield()
                        executor.execute(eff, ::sendIntent)
                    }
                }
            }
        }
    }

    /**
     * Отправляет одноразовые события через канал событий.
     * @param events набор событий для отправки
     */
    private fun executeEvents(events: Set<E>) = viewModelScope.launch {
        events.forEach { _events.send(it) }
    }

    /**
     * Обрабатывает интент и возвращает новый [ReduceResult].
     * Должен быть реализован в наследующих классах.
     *
     * @param intent интент для обработки
     * @param state текущее состояние
     * @return [ReduceResult] с новым состоянием, эффектами и событиями
     */
    protected abstract fun handleIntent(intent: I, state: S): ReduceResult<S, E, F>

    /**
     * Отправляет интент в обработку.
     * @param intent интент для отправки
     */
    fun sendIntent(intent: I) {
        viewModelScope.launch { _intents.emit(intent) }
    }

    /**
     * Обновляет состояние, запускает эффекты и отправляет события на основе [ReduceResult].
     * @param result результат обработки интента
     */
    private fun reduce(result: ReduceResult<S, E, F>) {
        _state.value = result.state
        if (result.effects.isNotEmpty()) executeEffects(result.effects)
        if (result.events.isNotEmpty()) executeEvents(result.events)
    }
}

