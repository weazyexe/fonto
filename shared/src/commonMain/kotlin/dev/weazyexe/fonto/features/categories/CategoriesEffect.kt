package dev.weazyexe.fonto.features.categories

import dev.weazyexe.fonto.arch.Effect

sealed interface CategoriesEffect : Effect {

    object ShowCategoryDeletionFailureMessage: CategoriesEffect

    object ShowCategoryDeletionSuccessMessage: CategoriesEffect
}