package dev.weazyexe.fonto.features.debug

import dev.weazyexe.fonto.arch.DomainState

data class DebugDomainState(
    val stub: String = ""
) : DomainState