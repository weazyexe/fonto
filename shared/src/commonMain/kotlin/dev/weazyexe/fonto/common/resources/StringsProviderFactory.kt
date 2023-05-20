package dev.weazyexe.fonto.common.resources

expect class StringsProviderFactory {

    fun create(): StringsProvider
}

fun createStringsProvider(factory: StringsProviderFactory): StringsProvider {
    return factory.create()
}