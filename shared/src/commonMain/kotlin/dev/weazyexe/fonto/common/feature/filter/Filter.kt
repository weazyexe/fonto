package dev.weazyexe.fonto.common.feature.filter

interface Filter

interface Bool<B: Bool<B>> : Filter {

    val value: Boolean

    fun toggle(): B
}

interface Single<T> : Filter {
    val value: T
    val possibleValues: List<T>

    fun <F : Single<T>> change(newValue: T): F
}