package dev.weazyexe.fonto.features.settings

import dev.weazyexe.fonto.arch.DomainState
import dev.weazyexe.fonto.common.model.backup.ExportStrategy
import dev.weazyexe.fonto.common.model.preference.Group

data class SettingsDomainState(
    val preferences: List<Group> = emptyList(),
    val exportStrategy: ExportStrategy = ExportStrategy(categories = true, feeds = true, posts = true),
    val isLoading: Boolean = false
) : DomainState
