package dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.R
import dev.weazyexe.fonto.common.core.asBitmap
import dev.weazyexe.fonto.common.core.asLocalImage
import dev.weazyexe.fonto.common.data.usecase.GetIconByRssUrlUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.CreateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.UpdateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.rss.IsRssValidUseCase
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.utils.isUrlValid
import dev.weazyexe.fonto.ui.core.presentation.CoreViewModel
import dev.weazyexe.fonto.ui.core.presentation.LoadState
import dev.weazyexe.fonto.ui.core.presentation.ResponseError
import dev.weazyexe.fonto.ui.features.destinations.AddEditFeedScreenDestination
import kotlinx.coroutines.launch

class AddEditFeedViewModel(
    savedStateHandle: SavedStateHandle,
    private val createFeed: CreateFeedUseCase,
    private val updateFeed: UpdateFeedUseCase,
    private val getIconByRssUrl: GetIconByRssUrlUseCase,
    private val isRssValid: IsRssValidUseCase
) : CoreViewModel<AddEditFeedState, AddEditFeedEffect>() {

    private val args = AddEditFeedScreenDestination.argsFrom(savedStateHandle)

    override val initialState: AddEditFeedState = AddEditFeedState(
        id = args.feed?.id,
        title = args.feed?.title.orEmpty(),
        link = args.feed?.link.orEmpty(),
        iconLoadState = args.feed?.icon?.asBitmap()?.let { LoadState.data(it) } ?: LoadState.initial()
    )

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

    fun finish() = viewModelScope.launch {
        if (state.title.isEmpty()) {
            setState { copy(finishLoadState = LoadState.error(ResponseError.FeedValidationError(R.string.error_feed_invalid_title))) }
            return@launch
        }

        if (state.link.isEmpty()) {
            setState { copy(finishLoadState = LoadState.error(ResponseError.FeedValidationError(R.string.error_feed_invalid_link))) }
            return@launch
        }

        setState { copy(finishLoadState = LoadState.loading()) }
        val isNewslineValid = request { isRssValid(state.link) }

        if (isNewslineValid.error != null) {
            setState { copy(finishLoadState = isNewslineValid) }
            return@launch
        }

        if (isNewslineValid.data == null) {
            setState { copy(finishLoadState = LoadState.error(ResponseError.UnknownError())) }
            return@launch
        }

        if (!isNewslineValid.data) {
            setState { copy(finishLoadState = LoadState.error(ResponseError.InvalidRssFeed())) }
            return@launch
        }

        saveChanges()
    }

    private fun saveChanges() = viewModelScope.launch {
        val image = state.iconLoadState.data?.asLocalImage()
        val response = request {
            val editFeedId = state.id
            if (editFeedId != null) {
                updateFeed(Feed(editFeedId, state.title.trim(), state.link.trim(), image))
            } else {
                createFeed(state.title, state.link, image)
            }
        }

        if (response.error != null) {
            setState { copy(finishLoadState = LoadState.error(response.error)) }
            return@launch
        }

        AddEditFeedEffect.NavigateUp(isSuccessful = true).emit()
    }
}