package dev.weazyexe.fonto.ui.navigation

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import dev.weazyexe.fonto.android.feature.feed.screens.feedDestinations
import dev.weazyexe.fonto.debug.DebugNavGraph
import dev.weazyexe.fonto.ui.features.NavGraphs

object AppNavGraph : NavGraphSpec {

    override val destinationsByRoute: Map<String, DestinationSpec<*>> = feedDestinations.associateBy { it.route }

    override val route: String = "app"

    override val startRoute: Route = NavGraphs.root

    override val nestedNavGraphs: List<NavGraphSpec> =
        listOf(
            NavGraphs.root,
            DebugNavGraph
        )
}
