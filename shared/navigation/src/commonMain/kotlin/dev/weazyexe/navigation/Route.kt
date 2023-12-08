package dev.weazyexe.navigation

sealed interface Route {

    sealed interface Feed : Route {

        data object Root : Feed

        data object Manage : Feed
    }
}
