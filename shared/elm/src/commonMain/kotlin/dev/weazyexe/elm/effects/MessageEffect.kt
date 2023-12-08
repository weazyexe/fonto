package dev.weazyexe.elm.effects

import dev.weazyexe.messenger.Messenger

class MessageEffect :
    Effect<Pair<Messenger, String>, Any> by Effect.onMain.idle({ (messenger, message) ->
        messenger.message(message)
    })
