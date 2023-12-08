package dev.weazyexe.messenger

import androidx.compose.material3.SnackbarHostState

class AndroidMessenger(
    private val snackbarHostState: SnackbarHostState
) : Messenger {

    override suspend fun message(message: String) {
        snackbarHostState.showSnackbar(message = message)
    }
}
