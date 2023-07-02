package dev.weazyexe.fonto.common.app.initializer

internal class AppInitializerImpl(
    private val categoriesInitializer: CategoriesInitializer,
    private val mockFeedInitializer: MockFeedInitializer,
    private val syncPostsInitializer: SyncPostsInitializer
) : AppInitializer {

    override suspend fun initialize(arguments: AppInitializer.Args) {
        categoriesInitializer.initialize()
        syncPostsInitializer.initialize()
        mockFeedInitializer.initialize(MockFeedInitializer.Args(arguments.areMockFeedsEnabled))
    }
}