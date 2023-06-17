@file:JvmName("CommonStringsProvider")
package dev.weazyexe.fonto.common.resources

internal interface StringsProvider {
    fun get(id: String, quantity: Int): String
    fun get(id: String): String
    fun format(id: String, vararg formatArgs: Any): String
}