package dev.weazyexe.messenger

import dev.weazyexe.messenger.provider.SnackbarHostStateProvider

class AndroidMessenger(
    private val snackbarHostStateProvider: SnackbarHostStateProvider
) : Messenger {

    override suspend fun message(message: String) {
        snackbarHostStateProvider.get().showSnackbar(message = message)
    }
}
