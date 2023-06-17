package dev.weazyexe.fonto.common.app

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
        stringsProvider.get("category_default_news"),
        stringsProvider.get("category_default_technologies"),
        stringsProvider.get("category_default_business"),
        stringsProvider.get("category_default_marketing"),
        stringsProvider.get("category_default_design"),
        stringsProvider.get("category_default_cyber_security"),
        stringsProvider.get("category_default_politics"),
        stringsProvider.get("category_default_science")
    )
}