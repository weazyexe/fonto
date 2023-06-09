package dev.weazyexe.fonto.arch

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Base [ViewModel] for all the screens in the app
 */
abstract class Presentation<S : DomainState, E : Effect> {

    private var _scope: CoroutineScope? = null
    protected val scope: CoroutineScope
        get() = _scope ?: error("Presentation layer wasn't initialized")

    /**
     * UI state
     */
    private val _uiState by lazy { MutableStateFlow(initialState) }
    val domainState: StateFlow<S>
        get() = _uiState.asStateFlow()
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
        _uiState.value = stateBuilder(domainState.value)
    }

    /**
     * Triggers side-effect
     */
    protected fun E.emit() = scope.launch {
        _effects.send(this@emit)
    }
}