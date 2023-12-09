package dev.weazyexe.fonto.di

import dev.weazyexe.fonto.ui.AppViewModel
import dev.weazyexe.fonto.util.asDirection
import dev.weazyexe.messenger.AndroidMessenger
import dev.weazyexe.messenger.Messenger
import dev.weazyexe.navigation.AndroidNavigator
import dev.weazyexe.navigation.Navigator
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { AppViewModel(get()) }

    single<Navigator> { params ->
        AndroidNavigator(
            context = androidContext(),
            navController = params.get(),
            activityResultRegistry = params.get(),
            routeMapper = { it.asDirection() }
        )
    }

    single<Messenger> { params ->
        AndroidMessenger(snackbarHostState = params.get())
    }
}
