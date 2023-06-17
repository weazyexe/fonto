package dev.weazyexe.fonto.common.app

internal class AppInitializerImpl(
    private val categoriesInitializer: CategoriesInitializer
) : AppInitializer {

    override suspend fun initialize() {
        categoriesInitializer.initialize()
    }
}