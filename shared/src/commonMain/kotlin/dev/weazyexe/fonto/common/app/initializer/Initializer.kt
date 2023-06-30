package dev.weazyexe.fonto.common.app.initializer

interface Initializer<T> {

    suspend fun initialize(arguments: T)
}

suspend fun Initializer<Unit>.initialize() = initialize(Unit)