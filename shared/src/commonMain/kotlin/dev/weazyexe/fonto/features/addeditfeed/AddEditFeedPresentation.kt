package dev.weazyexe.fonto.features.addeditfeed

import dev.weazyexe.fonto.arch.PresentationWithArgs
import dev.weazyexe.fonto.common.model.feed.Category

abstract class AddEditFeedPresentation :
    PresentationWithArgs<AddEditFeedDomainState, AddEditFeedEffect, AddEditFeedArgs>() {

    abstract fun loadCategories()

    abstract fun updateTitle(title: String)

    abstract fun updateCategory(category: Category?)

    abstract fun updateLink(link: String)

    abstract fun finish()

}