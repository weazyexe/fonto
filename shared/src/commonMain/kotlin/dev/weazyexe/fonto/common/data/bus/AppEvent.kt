package dev.weazyexe.fonto.common.data.bus

sealed interface AppEvent {

    object RefreshFeed : AppEvent
}