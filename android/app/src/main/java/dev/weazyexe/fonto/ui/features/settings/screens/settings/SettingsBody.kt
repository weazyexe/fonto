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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.core.ui.components.SmallProgressIndicator
import dev.weazyexe.fonto.core.ui.components.preferences.CustomValuePreferenceItem
import dev.weazyexe.fonto.core.ui.components.preferences.PreferencesGroup
import dev.weazyexe.fonto.core.ui.components.preferences.SwitchPreferenceItem
import dev.weazyexe.fonto.core.ui.components.preferences.TextPreferenceItem
import dev.weazyexe.fonto.core.ui.components.preferences.model.Group
import dev.weazyexe.fonto.core.ui.components.preferences.model.Preference
import dev.weazyexe.fonto.core.ui.components.preferences.model.Value
import dev.weazyexe.fonto.core.ui.utils.StringResources

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBody(
    settings: List<Group>,
    isLoading: Boolean,
    hiddenPreferences: List<Preference.Identifier>,
    rootPaddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
    onTextPreferenceClick: (Preference.Text) -> Unit,
    onSwitchPreferenceClick: (Preference.Switch, Boolean) -> Unit,
    onCustomPreferenceClick: (Preference.CustomValue<Value<*>>) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val lazyListState = rememberLazyListState()
    val groups = remember(settings) {
        settings
            .filter { group ->
                group.preferences.any { it.id !in hiddenPreferences }
            }
            .map { group ->
                group.copy(
                    preferences = group.preferences
                        .filter { it.id !in hiddenPreferences }
                )
            }
    }

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
                items = groups,
                key = { it.hashCode() }
            ) { group ->
                PreferencesGroup(title = stringResource(group.title)) {
                    group.preferences.forEach { pref ->
                        when (pref) {
                            is Preference.Text ->
                                TextPreferenceItem(
                                    title = stringResource(id = pref.title),
                                    description = stringResource(id = pref.subtitle),
                                    icon = pref.icon,
                                    onClick = { onTextPreferenceClick(pref) },
                                    modifier = Modifier.fillMaxWidth()
                                )

                            is Preference.Switch ->
                                SwitchPreferenceItem(
                                    title = stringResource(id = pref.title),
                                    description = stringResource(id = pref.subtitle),
                                    value = pref.value,
                                    icon = pref.icon,
                                    onValueChange = { onSwitchPreferenceClick(pref, it) }
                                )

                            is Preference.CustomValue<*> -> {
                                CustomValuePreferenceItem(
                                    title = stringResource(id = pref.title),
                                    description = stringResource(id = pref.subtitle),
                                    value = pref.value,
                                    icon = pref.icon,
                                    onClick = { onCustomPreferenceClick(pref as Preference.CustomValue<Value<*>>) },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}