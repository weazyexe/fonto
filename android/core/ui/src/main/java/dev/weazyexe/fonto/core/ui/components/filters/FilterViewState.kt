package dev.weazyexe.fonto.core.ui.components.filters

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.feature.filter.Filter

@Immutable
data class FilterViewState<F : Filter>(
    val filter: F,
    val title: String
)