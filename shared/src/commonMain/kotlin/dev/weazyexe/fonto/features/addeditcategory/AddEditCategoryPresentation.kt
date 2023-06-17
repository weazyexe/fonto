package dev.weazyexe.fonto.features.addeditcategory

import dev.weazyexe.fonto.arch.PresentationWithArgs

abstract class AddEditCategoryPresentation :
    PresentationWithArgs<AddEditCategoryDomainState, AddEditCategoryEffect, AddEditCategoryArgs>() {

    abstract fun onTitleChange(title: String)

    abstract fun saveChanges()

}