package dev.weazyexe.fonto.common.data

internal interface Validator<T> {

    fun validate(value: T): Boolean
}