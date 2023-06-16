package dev.weazyexe.fonto.ui.features.settings.screens.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.core.ui.components.SmallProgressIndicator
import dev.weazyexe.fonto.core.ui.components.preferences.CustomValuePreferenceItem
import dev.weazyexe.fonto.core.ui.components.preferences.PreferencesGroup
import dev.weazyexe.fonto.core.ui.components.preferences.SwitchPreferenceItem
import dev.weazyexe.fonto.core.ui.components.preferences.TextPreferenceItem
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.ui.features.settings.screens.settings.viewstate.GroupsViewState
import dev.weazyexe.fonto.ui.features.settings.screens.settings.viewstate.PreferenceViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBody(
    settings: GroupsViewState,
    isLoading: Boolean,
    rootPaddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
    onPreferenceClick: (PreferenceViewState) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier
            .padding(bottom = rootPaddingValues.calculateBottomPadding())
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = StringResources.home_bottom_label_settings)) },
                scrollBehavior = scrollBehavior,
                actions = {
                    if (isLoading) {
                        SmallProgressIndicator(modifier = Modifier.padding(end = 16.dp))
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(top = padding.calculateTopPadding()),
            state = lazyListState
        ) {
            items(
                items = settings.groups,
                key = { it.key }
            ) { group ->
                PreferencesGroup(title = group.title) {
                    group.preferences.forEach { pref ->
                        if (pref.isVisible) {
                            when (pref) {
                                is PreferenceViewState.Switch -> {
                                    SwitchPreferenceItem(
                                        title = pref.title,
                                        description = pref.description,
                                        value = pref.value,
                                        icon = pref.icon,
                                        onValueChange = { onPreferenceClick(pref.copy(value = it)) }
                                    )
                                }

                                is PreferenceViewState.Text -> {
                                    TextPreferenceItem(
                                        title = pref.title,
                                        description = pref.description,
                                        icon = pref.icon,
                                        onClick = { onPreferenceClick(pref) },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }

                                is PreferenceViewState.Value<*> -> {
                                    CustomValuePreferenceItem(
                                        title = pref.title,
                                        description = pref.description,
                                        displayValue = pref.displayValue,
                                        icon = pref.icon,
                                        onClick = { onPreferenceClick(pref) },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}