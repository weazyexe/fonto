package dev.weazyexe.fonto.features.addeditfeed

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.onError
import dev.weazyexe.fonto.common.data.onLoading
import dev.weazyexe.fonto.common.data.onSuccess
import dev.weazyexe.fonto.common.model.base.LocalImage
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal class AddEditFeedPresentationImpl(
    private val dependencies: AddEditFeedDependencies
) : AddEditFeedPresentation() {

    override val initialState: AddEditFeedDomainState
        get() = dependencies.initialState

    override fun onCreate(scope: CoroutineScope, navigationArguments: AddEditFeedArgs) {
        super.onCreate(scope, navigationArguments)
        loadFeed(navigationArguments.id)
        loadCategories()
        subscribeOnLinkChange()
    }

    override fun loadCategories() {
        dependencies.getAllCategories()
            .onError { AddEditFeedEffect.ShowCategoriesLoadingFailureMessage.emit() }
            .onSuccess { setState { copy(categories = it.data) } }
            .launchIn(scope)
    }

    override fun updateTitle(title: String) {
        setState { copy(title = title) }
    }

    override fun updateCategory(category: Category?) {
        setState { copy(category = category) }
    }

    override fun updateLink(link: String) {
        setState { copy(link = link) }
        state.debouncedLink.value = link
    }

    override fun finish() {
        if (!dependencies.titleValidator.validate(state.title)) {
            AddEditFeedEffect.ShowTitleInvalidMessage.emit()
            return
        }

        if (!dependencies.urlValidator.validate(state.link)) {
            AddEditFeedEffect.ShowLinkInvalidMessage.emit()
            return
        }

        val id = state.id
        if (id != null) {
            val feed = Feed(
                id = id,
                title = state.title.trim(),
                link = state.link.trim(),
                icon = (state.icon as? AsyncResult.Success)?.data,
                type = state.type,
                category = state.category
            )

            saveFeed(feed)
        } else {
            createFeed(
                title = state.title.trim(),
                link = state.link.trim(),
                category = state.category,
                icon = (state.icon as? AsyncResult.Success)?.data
            )
        }
    }

    private fun subscribeOnLinkChange() {
        state.debouncedLink
            .filter { it.isNotEmpty() && dependencies.urlValidator.validate(it) }
            .onEach { setState { copy(icon = AsyncResult.Loading()) } }
            .debounce(500L)
            .onEach { loadIcon(it) }
            .launchIn(scope)
    }

    private fun loadIcon(link: String) {
        dependencies.getFaviconByUrl(link)
            .onError { AddEditFeedEffect.ShowFaviconLoadingFailureMessage.emit() }
            .onEach { setState { copy(icon = it) } }
            .launchIn(scope)
    }

    private fun saveFeed(feed: Feed) {
        dependencies.updateFeed(feed)
            .onEach { setState { copy(finishResult = it) } }
            .onError {
                setState { copy(finishResult = it) }
                AddEditFeedEffect.ShowFeedSavingError.emit()
            }
            .onSuccess { AddEditFeedEffect.NavigateUp(isSuccessful = true).emit() }
            .launchIn(scope)
    }

    private fun createFeed(
        title: String,
        link: String,
        category: Category?,
        icon: LocalImage?
    ) {
        dependencies.getFeedType(link)
            .onLoading { setState { copy(finishResult = AsyncResult.Loading()) } }
            .onError {
                setState { copy(finishResult = AsyncResult.Error(it.error)) }
                AddEditFeedEffect.ShowFeedValidationError.emit()
            }
            .filterIsInstance<AsyncResult.Success<Feed.Type>>()
            .flatMapLatest { dependencies.createFeed(title, link, icon, it.data, category) }
            .onEach { setState { copy(finishResult = it) } }
            .onError {
                setState { copy(finishResult = it) }
                AddEditFeedEffect.ShowFeedSavingError.emit()
            }
            .onSuccess { AddEditFeedEffect.NavigateUp(isSuccessful = true).emit() }
            .launchIn(scope)
    }

    private fun loadFeed(id: Feed.Id?) {
        if (id == null) return

        dependencies.getFeed(id)
            .onError { AddEditFeedEffect.ShowFeedLoadingFailureMessage.emit() }
            .onSuccess {
                setState {
                    copy(
                        id = id,
                        title = it.data.title,
                        link = it.data.link,
                        category = it.data.category,
                        type = it.data.type,
                        icon = AsyncResult.Success(it.data.icon)
                    )
                }
            }
            .launchIn(scope)
    }
}