package dev.weazyexe.fonto.utils.extensions

import java.util.UUID

actual fun generateUUID(): String = UUID.randomUUID().toString()