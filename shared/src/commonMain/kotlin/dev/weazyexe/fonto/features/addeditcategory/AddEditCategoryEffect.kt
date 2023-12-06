package dev.weazyexe.fonto.features.addeditcategory

import dev.weazyexe.fonto.arch.Effect

sealed interface AddEditCategoryEffect : Effect {

    object NavigateUp : AddEditCategoryEffect
}
