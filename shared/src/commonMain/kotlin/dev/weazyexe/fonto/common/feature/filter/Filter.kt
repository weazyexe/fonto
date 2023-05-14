package dev.weazyexe.fonto.common.feature.filter

import kotlinx.datetime.Instant

interface Filter

interface Bool<B : Bool<B>> : Filter {

    val isEnabled: Boolean

    fun toggle(): B
}

interface Dates<D : Dates<D>> : Filter {

    val range: Range?

    fun change(range: Range?): D

    data class Range(
        val from: Instant,
        val to: Instant
    )
}

interface Multiple<T : Any, M : Multiple<T, M>> : Filter {
    val values: List<T>
    val possibleValues: List<T>

    fun change(newValue: List<T>, newPossibleValues: List<T>): M
}