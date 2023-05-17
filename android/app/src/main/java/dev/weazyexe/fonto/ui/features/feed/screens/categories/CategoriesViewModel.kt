package dev.weazyexe.fonto.ui.features.feed.screens.categories

import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel

class CategoriesViewModel : CoreViewModel<CategoriesState, CategoriesEffect>() {

    override val initialState: CategoriesState = CategoriesState()
}