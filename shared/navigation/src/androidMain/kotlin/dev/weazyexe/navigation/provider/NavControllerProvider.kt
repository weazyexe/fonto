package dev.weazyexe.navigation.provider

import androidx.navigation.NavController

class NavControllerProvider {

    private var navController: NavController? = null

    fun get(): NavController = navController ?: error("No NavController")

    fun set(navController: NavController) {
        this.navController = navController
    }
}
