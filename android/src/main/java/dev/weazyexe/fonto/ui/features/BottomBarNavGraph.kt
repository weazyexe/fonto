package dev.weazyexe.fonto.ui.features

import com.ramcosta.composedestinations.annotation.NavGraph

const val BOTTOM_BAR_NAV_GRAPH = "bottom_bar"

@NavGraph(route = BOTTOM_BAR_NAV_GRAPH)
annotation class BottomBarNavGraph(
    val start: Boolean = false
)