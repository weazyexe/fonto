package dev.weazyexe.messenger.provider

import androidx.compose.material3.SnackbarHostState

class SnackbarHostStateProvider {

    private var snackbarHostState: SnackbarHostState? = null

    fun get(): SnackbarHostState = snackbarHostState ?: error("No SnackbarHostState")

    fun set(snackbarHostState: SnackbarHostState) {
        this.snackbarHostState = snackbarHostState
    }
}
