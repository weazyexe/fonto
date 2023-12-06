package dev.weazyexe.fonto

import org.gradle.api.provider.Property

interface ComposeDestinationsExtensions {
    val mode: Property<Mode>
    val moduleName: Property<String>

    enum class Mode(val origin: String) {
        SingleModule("singlemodule"),
        NavGraphs("navgraphs")
    }
}
