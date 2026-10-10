package ru.blank.mobileappcore.network.provider

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Глобальный держатель состояния авторизации для централизованного управления разлогиниванием.
 *
 * ## Принцип работы:
 * 1. Интерсептор вызывает [emitUnauthorized] при необходимости разлогинивания
 * 2. UI слой подписывается на [unauthorizedFlow] и реагирует на события
 * 3. Приложение выполняет навигацию на экран авторизации
 */
object AuthStateHolder {
    private val _unauthorizedFlow = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1)
    val unauthorizedFlow = _unauthorizedFlow.asSharedFlow()

    suspend fun emitUnauthorized() {
        _unauthorizedFlow.emit(Unit)
    }
}
