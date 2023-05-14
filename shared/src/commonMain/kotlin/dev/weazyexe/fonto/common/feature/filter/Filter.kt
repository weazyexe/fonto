package dev.weazyexe.fonto.common.feature.filter

import kotlinx.datetime.Instant

interface Filter

interface Bool<B: Bool<B>> : Filter {

    val isEnabled: Boolean

    fun toggle(): B
}

interface Dates<D: Dates<D>>: Filter {

    val range: Range?

    fun change(range: Range?): D

    data class Range(
        val from: Instant,
        val to: Instant
    )
}

interface Single<T> : Filter {
    val value: T
    val possibleValues: List<T>

    fun <F : Single<T>> change(newValue: T): F
}