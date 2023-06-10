package dev.weazyexe.fonto.common.data

interface Validator<T> {

    fun validate(value: T): Boolean
}