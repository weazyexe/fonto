package dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.core.asBitmap
import dev.weazyexe.fonto.common.data.usecase.CreateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.GetIconByRssUrlUseCase
import dev.weazyexe.fonto.common.utils.isUrlValid
import dev.weazyexe.fonto.ui.core.presentation.CoreViewModel
import dev.weazyexe.fonto.ui.core.presentation.LoadState
import dev.weazyexe.fonto.ui.core.presentation.ResponseError
import kotlinx.coroutines.launch

class AddEditFeedViewModel(
    private val createFeed: CreateFeedUseCase,
    private val getIconByRssUrl: GetIconByRssUrlUseCase
) : CoreViewModel<AddEditFeedState, AddEditFeedEffect>() {

    override val initialState: AddEditFeedState = AddEditFeedState()

    fun updateTitle(title: String) {
        setState { copy(title = title) }
    }

    fun updateLink(link: String) = viewModelScope.launch {
        setState { copy(link = link) }

        if (link.isUrlValid()) {
            setState { copy(iconLoadState = LoadState.loading()) }
            val icon = request {
                val rawBytesImage = getIconByRssUrl(link)
                val bitmap = rawBytesImage.asBitmap()
                if (bitmap != null) {
                    return@request bitmap
                } else {
                    throw ResponseError.FeedIconError()
                }
            }
            setState { copy(iconLoadState = icon) }
        }
    }

    fun finish() {

    }
}