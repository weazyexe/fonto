package dev.weazyexe.fonto.features.debug

import dev.weazyexe.fonto.arch.Presentation

abstract class DebugPresentation : Presentation<DebugDomainState, DebugEffect>() {

    abstract fun addMockFeeds()

    abstract fun addPartialInvalidMockFeeds()

    abstract fun addInvalidMockFeeds()
}