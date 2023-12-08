package dev.weazyexe.messenger

interface Messenger {

    suspend fun message(message: String)
}
