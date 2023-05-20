package dev.weazyexe.fonto.common.app

class AppInitializer(
    private val categoriesInitializer: CategoriesInitializer
) : Initializer {

    override suspend fun initialize() {
        categoriesInitializer.initialize()
    }
}