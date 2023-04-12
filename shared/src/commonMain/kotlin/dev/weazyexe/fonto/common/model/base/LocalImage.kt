package dev.weazyexe.fonto.common.model.base

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class LocalImage(val bytes: ByteArray)