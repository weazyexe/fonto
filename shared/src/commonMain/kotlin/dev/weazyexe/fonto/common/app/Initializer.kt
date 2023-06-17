package dev.weazyexe.fonto.common.app

interface Initializer<T> {

    suspend fun initialize(arguments: T)
}