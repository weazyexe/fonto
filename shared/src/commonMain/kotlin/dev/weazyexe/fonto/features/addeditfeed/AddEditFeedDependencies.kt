package dev.weazyexe.fonto.features.addeditfeed

import dev.weazyexe.fonto.common.data.usecase.category.GetAllCategoriesUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.CreateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedTypeUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.UpdateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.icon.GetFaviconByUrlUseCase
import dev.weazyexe.fonto.features.addeditfeed.validator.TitleValidator
import dev.weazyexe.fonto.features.addeditfeed.validator.UrlValidator

internal data class AddEditFeedDependencies(
    val initialState: AddEditFeedDomainState,

    val getFeed: GetFeedUseCase,
    val createFeed: CreateFeedUseCase,
    val updateFeed: UpdateFeedUseCase,
    val getFaviconByUrl: GetFaviconByUrlUseCase,
    val getFeedType: GetFeedTypeUseCase,
    val getAllCategories: GetAllCategoriesUseCase,

    val titleValidator: TitleValidator,
    val urlValidator: UrlValidator
)