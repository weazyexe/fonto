package dev.weazyexe.fonto.features.categories

import dev.weazyexe.fonto.arch.Presentation
import dev.weazyexe.fonto.common.model.feed.Category

abstract class CategoriesPresentation : Presentation<CategoriesDomainState, CategoriesEffect>() {

    abstract fun loadFeedsAndCategories()

    abstract fun deleteById(id: Category.Id)

    abstract fun updateHasChanges(hasChanges: Boolean)
}