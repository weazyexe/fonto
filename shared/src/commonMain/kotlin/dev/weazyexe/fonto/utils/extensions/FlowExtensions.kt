package dev.weazyexe.fonto.utils.extensions

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.onError
import dev.weazyexe.fonto.common.data.onSuccess
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn

internal inline fun <T> flowIo(crossinline block: suspend FlowCollector<AsyncResult<T>>.() -> Unit) =
    flow { block() }
        .flowOn(Dispatchers.IO)
        .catch {
            Napier.e(throwable = it, message = "Error in flowIo")
            emit(AsyncResult.Error(it.asResponseError()))
        }

internal suspend inline fun <T> Flow<AsyncResult<T>>.awaitSuccess(): AsyncResult.Success<T>? {
    val channelFlow = callbackFlow {
        this@awaitSuccess
            .onError {
                send(it)
                close()
            }
            .onSuccess {
                send(it)
                close()
            }
            .launchIn(this)

        awaitClose()
    }

    return channelFlow.firstOrNull() as? AsyncResult.Success<T>
}