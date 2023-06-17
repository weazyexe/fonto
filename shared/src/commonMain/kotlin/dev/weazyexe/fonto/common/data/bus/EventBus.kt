package dev.weazyexe.fonto.common.data.bus

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

internal class EventBus {

    private val _events = MutableSharedFlow<AppEvent>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )
    private val events: SharedFlow<AppEvent>
        get() = _events.asSharedFlow()

    fun observe(): Flow<AppEvent> = events

    fun emit(event: AppEvent) {
        _events.tryEmit(event)
    }
}