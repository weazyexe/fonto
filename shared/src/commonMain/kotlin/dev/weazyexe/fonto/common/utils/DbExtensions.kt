package dev.weazyexe.fonto.common.utils

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

fun <T : Any> Query<T>.flowList(): Flow<List<T>> =
    this.asFlow().mapToList(Dispatchers.IO)