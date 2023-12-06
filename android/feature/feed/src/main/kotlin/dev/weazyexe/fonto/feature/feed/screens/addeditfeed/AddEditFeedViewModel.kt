package dev.weazyexe.fonto.feature.feed.screens.addeditfeed

import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.core.asBitmap
import dev.weazyexe.fonto.common.data.map
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.core.ui.mapState
import dev.weazyexe.fonto.feature.feed.screens.destinations.AddEditFeedScreenDestination
import dev.weazyexe.fonto.features.addeditfeed.AddEditFeedArgs
import dev.weazyexe.fonto.features.addeditfeed.AddEditFeedDomainState
import dev.weazyexe.fonto.features.addeditfeed.AddEditFeedPresentation

class AddEditFeedViewModel(
    private val presentation: AddEditFeedPresentation,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = AddEditFeedScreenDestination.argsFrom(savedStateHandle)

    val state = presentation.domainState.mapState { it.asViewState() }
    val effects = presentation.effects

    init {
        presentation.onCreate(viewModelScope, AddEditFeedArgs(args.feedId))
    }

    fun loadCategories() {
        presentation.loadCategories()
    }

    fun updateTitle(title: String) {
        presentation.updateTitle(title)
    }

    fun updateCategory(category: Category?) {
        presentation.updateCategory(category)
    }

    fun updateLink(link: String) {
        presentation.updateLink(link)
    }

    fun finish() {
        presentation.finish()
    }

    private fun AddEditFeedDomainState.asViewState() =
        AddEditFeedViewState(
            title = title,
            link = link,
            isEditMode = id != null,
            category = category,
            categories = categories,
            icon = icon.map { it?.asBitmap()?.asImageBitmap() },
            finishResult = finishResult
        )
}
