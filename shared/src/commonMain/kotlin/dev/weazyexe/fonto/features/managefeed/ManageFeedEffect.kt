package dev.weazyexe.fonto.features.managefeed

import dev.weazyexe.fonto.arch.Effect

sealed interface ManageFeedEffect : Effect {

    object ShowDeletedSuccessfullyMessage : ManageFeedEffect

    object ShowDeletionFailedMessage : ManageFeedEffect
}
