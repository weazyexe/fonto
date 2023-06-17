package dev.weazyexe.fonto.common.app

internal class AppInitializerImpl(
    private val categoriesInitializer: CategoriesInitializer,
    private val mockFeedInitializer: MockFeedInitializer
) : AppInitializer {

    override suspend fun initialize(arguments: AppInitializer.Args) {
        categoriesInitializer.initialize(Unit)
        mockFeedInitializer.initialize(MockFeedInitializer.Args(arguments.areMockFeedsEnabled))
    }
}