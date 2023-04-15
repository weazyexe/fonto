package dev.weazyexe.fonto.ui.features.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.ui.features.settings.components.TextPreferenceItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBody(onDebugClick: () -> Unit) {
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TextPreferenceItem(
                title = "Debug screen",
                description = "What?",
                icon = R.drawable.ic_bug_24,
                onClick = onDebugClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}