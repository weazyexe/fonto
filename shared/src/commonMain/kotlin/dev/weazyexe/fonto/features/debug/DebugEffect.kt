package dev.weazyexe.fonto.features.debug

import dev.weazyexe.fonto.arch.Effect

interface DebugEffect : Effect {

    object ShowFeedsAddedSuccessfullyMessage : DebugEffect

    object ShowPostsDeletedSuccessfullyMessage: DebugEffect
}
