package dev.weazyexe.fonto.common.app

class AppInitializerImpl(
    private val categoriesInitializer: CategoriesInitializer
) : AppInitializer {

    override suspend fun initialize() {
        categoriesInitializer.initialize()
    }
}