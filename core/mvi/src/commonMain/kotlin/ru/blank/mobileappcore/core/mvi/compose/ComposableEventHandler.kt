package ru.blank.mobileappcore.core.mvi.compose

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow

/**
 * Обработчик одноразовых событий в UI.
 *
 * Пример использования:
 * ```
 * @Composable
 * internal fun Screen(
 *     viewModel: FeatureViewModel,
 *     navigateToNext: () -> Unit
 * ) {
 *     val state by viewModel.state.collectAsStateWithLifecycle()
 *
 *     HandleEvents(
 *         events = viewModel.events,
 *         navigateToNext = navigateToNext
 *     )
 *
 *     Content(
 *         state = state,
 *         send = viewModel::sendIntent
 *     )
 * }
 *
 * @Composable
 * private fun HandleEvents(
 *     events: Flow<Event>,
 *     navigateToNext: () -> Unit
 * ) {
 *     ComposableEventHandler(events) { event ->
 *         when (event) {
 *             is Event.NavigateToNext -> navigateToNext()
 *             is Event.ShowToast -> toast.show(event.message)
 *         }
 *     }
 * }
 * ```
 *
 * @param T тип событий,
 * должен реализовывать интерфейс [ru.blank.mobileappcore.core.mvi.label.BaseEvent]
 * @param events Flow событий для обработки (например, viewModel.events)
 * @param handle лямбда-обработчик событий с логикой для каждого типа события
 */
@Composable
expect fun <T> ComposableEventHandler(events: Flow<T>, handle: (T) -> Unit)

