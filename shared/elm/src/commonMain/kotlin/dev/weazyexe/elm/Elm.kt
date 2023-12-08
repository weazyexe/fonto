package dev.weazyexe.elm

import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class Elm<State : Any, Message : Any, Dependencies : Any>(
    init: Update<State, Message, Dependencies>,
    update: (Message, State, Dependencies) -> Update<State, Message, Dependencies>,
    restore: (State) -> Update<State, Message, Dependencies>,
    dependencies: Dependencies,
) {

    private val tag = this::class.simpleName

    private val runtime by lazy {
        val result = ElmRuntime(
            init = {
                val currentState = _state.value
                if (currentState == null) init
                else restore(currentState)
            },
            update = update,
            dependencies = dependencies,
            exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                Napier.e(throwable, tag) { "Unhandled exception" }
            }
        )
        result.listenState { _state.tryEmit(it) }
        result
    }

    private val _state: MutableStateFlow<State> = MutableStateFlow(init.state)
    val state: StateFlow<State>
        get() = _state.asStateFlow()

    open fun onCreated() {
        runtime.ensureActive()
    }

    open fun onCleared() {
        runtime.cancel()
    }

    fun dispatch(message: Message) = runtime.dispatch(message)
}
