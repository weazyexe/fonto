@file:JvmName("CommonStringsProvider")
package dev.weazyexe.fonto.common.resources

internal interface StringsProvider {
    fun plural(id: String, quantity: Int, vararg args: Any): String
    fun string(id: String, vararg formatArgs: Any): String
}