package dev.weazyexe.fonto.common.app.initializer

import dev.weazyexe.fonto.common.data.usecase.category.CreateCategoryUseCase
import dev.weazyexe.fonto.common.feature.settings.SettingsStorage
import dev.weazyexe.fonto.common.resources.StringsProvider

internal class CategoriesInitializer(
    private val settings: SettingsStorage,
    private val createCategory: CreateCategoryUseCase,
    private val stringsProvider: StringsProvider
) : Initializer<Unit> {


    override suspend fun initialize(arguments: Unit) {
        if (!settings.isAppInitialized()) {
            buildDefaultCategories().forEach {
                createCategory(it)
            }
            settings.saveAppInitialized(isInitialized = true)
        }
    }

    private fun buildDefaultCategories(): List<String> = listOf(
        stringsProvider.string("category_default_news"),
        stringsProvider.string("category_default_technologies"),
        stringsProvider.string("category_default_business"),
        stringsProvider.string("category_default_marketing"),
        stringsProvider.string("category_default_design"),
        stringsProvider.string("category_default_cyber_security"),
        stringsProvider.string("category_default_politics"),
        stringsProvider.string("category_default_science")
    )
}