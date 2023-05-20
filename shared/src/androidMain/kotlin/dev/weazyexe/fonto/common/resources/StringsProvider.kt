package dev.weazyexe.fonto.common.resources

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("DiscouragedApi")
class AndroidStringsProvider(
    private val context: Context
) : StringsProvider {

    override fun get(id: String, quantity: Int): String {
        val resourceId = context.resources.getIdentifier(id, "plurals", context.packageName)
        if (resourceId == 0) return id
        return context.resources.getQuantityString(resourceId, quantity, quantity)
    }

    override fun get(id: String): String {
        val resourceId = context.resources.getIdentifier(id, "string", context.packageName)
        if (resourceId == 0) return id
        return context.getString(resourceId)
    }

    override fun format(id: String, vararg formatArgs: Any): String {
        val resourceId = context.resources.getIdentifier(id, "string", context.packageName)
        if (resourceId == 0) return id
        return context.resources.getString(resourceId, *formatArgs)
    }
}