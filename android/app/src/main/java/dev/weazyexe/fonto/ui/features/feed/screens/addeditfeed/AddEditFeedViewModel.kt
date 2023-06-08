package dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.app.App
import dev.weazyexe.fonto.common.core.asBitmap
import dev.weazyexe.fonto.common.core.asLocalImage
import dev.weazyexe.fonto.common.data.ResponseError
import dev.weazyexe.fonto.common.data.usecase.category.GetAllCategoriesUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.CreateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedTypeUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.UpdateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.icon.GetFaviconByUrlUseCase
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.utils.isUrlValid
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.ui.features.destinations.AddEditFeedScreenDestination
import kotlinx.coroutines.launch

class AddEditFeedViewModel(
    private val context: App,
    private val getFeed: GetFeedUseCase,
    private val createFeed: CreateFeedUseCase,
    private val updateFeed: UpdateFeedUseCase,
    private val getFaviconByUrl: GetFaviconByUrlUseCase,
    private val getFeedType: GetFeedTypeUseCase,
    private val getAllCategories: GetAllCategoriesUseCase,
    savedStateHandle: SavedStateHandle
) : CoreViewModel<AddEditFeedState, AddEditFeedEffect>() {

    private val args = AddEditFeedScreenDestination.argsFrom(savedStateHandle)

    override val initialState: AddEditFeedState = AddEditFeedState(id = args.feedId)

    init {
        loadCategories()
        args.feedId?.let { loadFeed(it) }
    }

    fun loadFeed(id: Feed.Id) = viewModelScope.launch {
        val feed = request { getFeed(id) }
            .withErrorHandling {
                AddEditFeedEffect.ShowMessage(StringResources.add_edit_feed_feed_loading_failure)
                    .emit()
            }?.data ?: return@launch

        setState {
            copy(
                title = feed.title,
                link = feed.link,
                category = feed.category,
                iconLoadState = LoadState.Data(feed.icon?.asBitmap())
            )
        }
    }

    fun loadCategories() = viewModelScope.launch {
        val categories = request { getAllCategories() }
            .withErrorHandling {
                AddEditFeedEffect.ShowMessage(StringResources.add_edit_feed_categories_loading_failure)
                    .emit()
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
                val rawBytesImage = getFaviconByUrl(link)
                rawBytesImage?.asBitmap()
            }.withErrorHandling {
                setState { copy(iconLoadState = LoadState.Error(it)) }
                // FIXME #35
                AddEditFeedEffect.ShowMessage(StringResources.error_unknown_message).emit()
            } ?: return@launch

            setState { copy(iconLoadState = icon) }
        }
    }

    fun updateCategory(category: Category?) {
        setState { copy(category = category) }
    }

    fun showCategoryAddedMessage() {
        AddEditFeedEffect.ShowMessage(StringResources.categories_category_has_been_saved).emit()
    }

    fun finish() = viewModelScope.launch {
        if (state.title.isEmpty()) {
            setState {
                copy(
                    // FIXME #35
                    finishLoadState = LoadState.Error(
                        ResponseError.FeedValidationError(
                            context.getString(StringResources.error_feed_invalid_title)
                        )
                    )
                )
            }
            return@launch
        }

        if (state.link.isEmpty()) {
            setState {
                copy(
                    // FIXME #35
                    finishLoadState = LoadState.Error(
                        ResponseError.FeedValidationError(
                            context.getString(StringResources.error_feed_invalid_link)
                        )
                    )
                )
            }
            return@launch
        }

        setState { copy(finishLoadState = LoadState.Loading()) }
        val feedType = request { getFeedType(state.link) }
            .withErrorHandling {
                setState { copy(finishLoadState = LoadState.Error(it)) }
            } ?: return@launch

        val data = feedType.data
        if (data == null) {
            // FIXME #35
            setState { copy(finishLoadState = LoadState.Error(ResponseError.InvalidRssFeed)) }
            return@launch
        }

        saveChanges(data)
    }

    private fun saveChanges(type: Feed.Type) = viewModelScope.launch {
        val image = (state.iconLoadState as? LoadState.Data)?.data?.asLocalImage()

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
                        category = state.category
                    )
                )
            } else {
                createFeed(state.title, state.link, image, type, state.category)
            }
        }.withErrorHandling {
            setState { copy(finishLoadState = LoadState.Error(it)) }
        } ?: return@launch

        AddEditFeedEffect.NavigateUp(isSuccessful = true).emit()
    }
}
