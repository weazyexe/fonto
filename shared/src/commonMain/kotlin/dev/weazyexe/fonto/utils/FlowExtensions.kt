package dev.weazyexe.fonto.utils

import dev.weazyexe.fonto.common.data.AsyncResult
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

inline fun <T> flowIo(crossinline block: suspend FlowCollector<AsyncResult<T>>.() -> Unit) =
    flow { block() }
        .flowOn(Dispatchers.IO)
        .catch {
            Napier.e(throwable = it, message = "Error in flowIo")
            emit(AsyncResult.Error(it.asResponseError()))
        }