package dev.weazyexe.fonto.ui.features.feed.screens.daterangepicker

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import dev.weazyexe.fonto.common.utils.HUMAN_READABLE_DATE_FORMAT
import dev.weazyexe.fonto.common.utils.format
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.animation.FullScreenDialogAnimationStyle
import dev.weazyexe.fonto.core.ui.components.CloseDialogButton
import kotlinx.datetime.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Destination(style = FullScreenDialogAnimationStyle::class)
@Composable
fun DateRangePickerDialog(
    resultBackNavigator: ResultBackNavigator<DateRangeResults?>
) {
    val state = rememberDateRangePickerState()

    val areDatesValid by remember {
        derivedStateOf { state.selectedStartDateMillis != null && state.selectedEndDateMillis != null }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.date_range_picker_dialog_title)) },
                navigationIcon = { CloseDialogButton { resultBackNavigator.navigateBack(result = null) } },
                actions = {
                    TextButton(
                        onClick = {
                            val results = DateRangeResults(
                                from = state.selectedStartDateMillis?.asInstant() ?: return@TextButton,
                                to = state.selectedEndDateMillis?.asInstant() ?: return@TextButton,
                            )
                            resultBackNavigator.navigateBack(result = results)
                        },
                        enabled = areDatesValid
                    ) {
                        Text(text = stringResource(R.string.date_range_picker_dialog_pick))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                        elevation = 3.dp
                    )
                )
            )
        }
    ) { padding ->
        DateRangePicker(
            state = state,
            modifier = Modifier.padding(padding),
            title = null,
            headline = {
                Text(
                    text = when {
                        areDatesValid -> {
                            val from = state.selectedStartDateMillis?.asInstant() ?: return@DateRangePicker
                            val to = state.selectedEndDateMillis?.asInstant() ?: return@DateRangePicker

                            stringResource(
                                id = R.string.feed_filters_dates_value,
                                from.format(HUMAN_READABLE_DATE_FORMAT),
                                to.format(HUMAN_READABLE_DATE_FORMAT),
                            )
                        }

                        state.selectedStartDateMillis != null -> {
                            val from = state.selectedStartDateMillis?.asInstant() ?: return@DateRangePicker
                            from.format(HUMAN_READABLE_DATE_FORMAT)
                        }

                        else -> ""
                    },
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        )
    }
}

private fun Long.asInstant(): Instant =
    Instant.fromEpochMilliseconds(this)
