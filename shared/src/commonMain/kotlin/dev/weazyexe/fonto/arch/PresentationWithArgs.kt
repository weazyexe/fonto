package dev.weazyexe.fonto.arch

import androidx.annotation.CallSuper
import kotlinx.coroutines.CoroutineScope

abstract class PresentationWithArgs<S : DomainState, E : Effect, NA : NavigationArguments> :
    Presentation<S, E>() {

    @CallSuper
    open fun onCreate(scope: CoroutineScope, navigationArguments: NA) {
        onCreate(scope)
    }

    final override fun onCreate(scope: CoroutineScope) {
        super.onCreate(scope)
    }
}