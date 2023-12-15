package dev.weazyexe.fonto.di

import dev.weazyexe.fonto.app.App
import dev.weazyexe.fonto.ui.AppViewModel
import dev.weazyexe.fonto.util.asDirection
import dev.weazyexe.messenger.AndroidMessenger
import dev.weazyexe.messenger.Messenger
import dev.weazyexe.messenger.provider.SnackbarHostStateProvider
import dev.weazyexe.navigation.AndroidNavigator
import dev.weazyexe.navigation.Navigator
import dev.weazyexe.navigation.provider.ActivityResultRegistryProvider
import dev.weazyexe.navigation.provider.NavControllerProvider
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { AppViewModel(get()) }

    single { (androidContext() as App).activityProvider }

    single {
        ActivityResultRegistryProvider(
            activityProvider = get()
        )
    }

    single { NavControllerProvider() }

    single { SnackbarHostStateProvider() }

    single<Navigator> {
        AndroidNavigator(
            context = androidContext(),
            navControllerProvider = get(),
            activityResultRegistryProvider = get(),
            routeMapper = { it.asDirection() }
        )
    }

    single<Messenger> {
        AndroidMessenger(snackbarHostStateProvider = get())
    }
}
