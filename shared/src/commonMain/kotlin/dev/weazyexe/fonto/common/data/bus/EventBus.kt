package dev.weazyexe.fonto.common.data.bus

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

internal class EventBus {

    private val _events = MutableSharedFlow<AppEvent>()
    private val events: SharedFlow<AppEvent>
        get() = _events.asSharedFlow()

    fun observe(): Flow<AppEvent> = events

    suspend fun emit(event: AppEvent) {
        _events.emit(event)
    }
}