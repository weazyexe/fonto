package dev.weazyexe.fonto.common.resources

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("DiscouragedApi")
internal class AndroidStringsProvider(
    private val context: Context
) : StringsProvider {

    override fun plural(id: String, quantity: Int, vararg args: Any): String {
        val resourceId = context.resources.getIdentifier(id, "plurals", context.packageName)
        if (resourceId == 0) return id
        return context.resources.getQuantityString(resourceId, quantity, quantity, args)
    }

    override fun string(id: String, vararg formatArgs: Any): String {
        val resourceId = context.resources.getIdentifier(id, "string", context.packageName)
        if (resourceId == 0) return id
        return context.resources.getString(resourceId, *formatArgs)
    }
}