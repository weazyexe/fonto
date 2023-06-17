package dev.weazyexe.fonto.common.resources

internal expect class StringsProviderFactory {

    fun create(): StringsProvider
}

internal fun createStringsProvider(factory: StringsProviderFactory): StringsProvider {
    return factory.create()
}