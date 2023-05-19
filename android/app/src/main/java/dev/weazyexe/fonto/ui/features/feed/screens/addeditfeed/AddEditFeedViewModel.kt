package dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.core.asBitmap
import dev.weazyexe.fonto.common.core.asLocalImage
import dev.weazyexe.fonto.common.data.usecase.category.GetAllCategoriesUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.CreateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedIconUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedTypeUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.UpdateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.icon.GetIconByRssUrlUseCase
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.utils.isUrlValid
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.presentation.ResponseError
import dev.weazyexe.fonto.core.ui.presentation.asViewState
import dev.weazyexe.fonto.ui.features.destinations.AddEditFeedScreenDestination
import kotlinx.coroutines.launch

class AddEditFeedViewModel(
    savedStateHandle: SavedStateHandle,
    private val createFeed: CreateFeedUseCase,
    private val updateFeed: UpdateFeedUseCase,
    private val getIconByRssUrl: GetIconByRssUrlUseCase,
    private val getFeedType: GetFeedTypeUseCase,
    private val getFeedIcon: GetFeedIconUseCase,
    private val getAllCategories: GetAllCategoriesUseCase
) : CoreViewModel<AddEditFeedState, AddEditFeedEffect>() {

    private val args = AddEditFeedScreenDestination.argsFrom(savedStateHandle)

    override val initialState: AddEditFeedState = AddEditFeedState(
        id = args.feedId,
        title = args.feedTitle,
        link = args.feedLink
    )

    init {
        loadCategories()
        args.feedId?.let { fetchFeedIcon(it) }
    }

    fun loadCategories() = viewModelScope.launch {
        val categories = request { getAllCategories() }
            .withErrorHandling {
                AddEditFeedEffect.ShowMessage(R.string.add_edit_feed_categories_loading_failure).emit()
            }?.data ?: return@launch

        setState { copy(categories = categories) }
    }

    fun updateTitle(title: String) {
        setState { copy(title = title) }
    }

    fun updateLink(link: String) = viewModelScope.launch {
        setState { copy(link = link) }

        if (link.isUrlValid()) {
            setState { copy(iconLoadState = LoadState.Loading()) }
            val icon = request {
                val rawBytesImage = getIconByRssUrl(link)
                rawBytesImage.asBitmap()
            }.withErrorHandling {
                setState { copy(iconLoadState = LoadState.Error(it)) }
                AddEditFeedEffect.ShowMessage(it.errorMessage).emit()
            } ?: return@launch

            setState { copy(iconLoadState = icon) }
        }
    }

    fun updateCategory(category: Category?) {
        setState { copy(category = category) }
    }

    fun finish() = viewModelScope.launch {
        if (state.title.isEmpty()) {
            setState { copy(finishLoadState = LoadState.Error(ResponseError.FeedValidationError(R.string.error_feed_invalid_title))) }
            return@launch
        }

        if (state.link.isEmpty()) {
            setState { copy(finishLoadState = LoadState.Error(ResponseError.FeedValidationError(R.string.error_feed_invalid_link))) }
            return@launch
        }

        setState { copy(finishLoadState = LoadState.Loading()) }
        val feedType = request { getFeedType(state.link) }
            .withErrorHandling {
                setState { copy(finishLoadState = LoadState.Error(it)) }
            } ?: return@launch

        val data = feedType.data
        if (data == null) {
            setState { copy(finishLoadState = LoadState.Error(ResponseError.InvalidRssFeed())) }
            return@launch
        }

        saveChanges(data)
    }

    private fun saveChanges(type: Feed.Type) = viewModelScope.launch {
        val image = (state.iconLoadState as? LoadState.Data)?.data?.asLocalImage()
        val category = state.category ?: return@launch

        request {
            val editFeedId = state.id
            if (editFeedId != null) {
                updateFeed(
                    Feed(
                        id = editFeedId,
                        title = state.title.trim(),
                        link = state.link.trim(),
                        icon = image,
                        type = type,
                        category = category
                    )
                )
            } else {
                createFeed(state.title, state.link, image, type)
            }
        }.withErrorHandling {
            setState { copy(finishLoadState = LoadState.Error(it)) }
        } ?: return@launch

        AddEditFeedEffect.NavigateUp(isSuccessful = true).emit()
    }

    private fun fetchFeedIcon(id: Feed.Id) = viewModelScope.launch {
        val icon = request { getFeedIcon(id) }
            .withErrorHandling { }

        if (icon is LoadState.Data) {
            setState { copy(iconLoadState = icon.asViewState { it?.asBitmap() }) }
        }
    }
}
