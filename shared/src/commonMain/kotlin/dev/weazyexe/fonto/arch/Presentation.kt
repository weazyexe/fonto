package dev.weazyexe.fonto.arch

import androidx.annotation.CallSuper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Presentation entities contains all the screen logic
 * and provides the screen state and side-effects
 */
abstract class Presentation<S : DomainState, E : Effect> {

    private var _scope: CoroutineScope? = null
    protected val scope: CoroutineScope
        get() = _scope ?: error("Presentation layer wasn't initialized")

    /**
     * UI state
     */
    private val _state by lazy { MutableStateFlow(initialState) }

    /**
     * Domain state for external usage
     */
    val domainState: StateFlow<S>
        get() = _state.asStateFlow()

    /**
     * Domain state for internal usage
     */
    protected val state: S
        get() = domainState.value

    /**
     * Side effects to screen channel
     */
    private val _effects = Channel<E>(Channel.BUFFERED)
    val effects: Flow<E>
        get() = _effects.receiveAsFlow()

    /**
     * Initial screen state
     */
    protected abstract val initialState: S

    @CallSuper
    open fun onCreate(scope: CoroutineScope) {
        _scope = scope
    }

    /**
     * Updates the screen state
     */
    protected fun setState(stateBuilder: S.() -> S) {
        _state.value = stateBuilder(domainState.value)
    }

    /**
     * Triggers side-effect
     */
    protected fun E.emit() = scope.launch {
        _effects.send(this@emit)
    }
}