package dev.weazyexe.fonto.ui.screens

import com.ramcosta.composedestinations.annotation.NavGraph

const val HOME_NAV_GRAPH = "home"

@NavGraph(route = HOME_NAV_GRAPH, default = true)
annotation class HomeNavGraph(
    val start: Boolean = false
)