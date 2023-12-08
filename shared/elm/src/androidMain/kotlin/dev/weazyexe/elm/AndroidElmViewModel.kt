package dev.weazyexe.elm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class AndroidElmViewModel<State : Any, ViewState : Any, Message : Any, Dependencies : Any>(
    private val elm: Elm<State, Message, Dependencies>
) : ViewModel() {

    val state: StateFlow<ViewState>
        get() = elm.state.mapState { convertToViewState(it) }

    init {
        elm.onCreated()
    }

    abstract fun convertToViewState(state: State): ViewState

    fun dispatch(message: Message) = elm.dispatch(message)

    override fun onCleared() {
        super.onCleared()
        elm.onCleared()
    }
}
