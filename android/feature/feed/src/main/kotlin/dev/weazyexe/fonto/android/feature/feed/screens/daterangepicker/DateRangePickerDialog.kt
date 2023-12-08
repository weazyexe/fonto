package dev.weazyexe.fonto.android.feature.feed.screens.daterangepicker

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import dev.weazyexe.fonto.common.utils.HUMAN_READABLE_DATE_FORMAT
import dev.weazyexe.fonto.common.utils.format
import dev.weazyexe.fonto.core.ui.animation.FullScreenDialogAnimationStyle
import dev.weazyexe.fonto.core.ui.components.toolbar.FullScreenDialogToolbar
import dev.weazyexe.fonto.core.ui.utils.StringResources
import kotlinx.datetime.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Destination(style = FullScreenDialogAnimationStyle::class)
@Composable
fun DateRangePickerDialog(
    resultBackNavigator: ResultBackNavigator<DateRangeResults?>
) {
    val state = rememberDateRangePickerState()
    Scaffold(
        topBar = {
            FullScreenDialogToolbar(
                title = stringResource(StringResources.date_range_picker_dialog_title),
                doneButtonText = stringResource(StringResources.date_range_picker_dialog_pick),
                onBackClick = { resultBackNavigator.navigateBack(result = null) },
                onDoneClick = {
                    val results = DateRangeResults(
                        from = state.selectedStartDateMillis?.asInstant()
                            ?: return@FullScreenDialogToolbar,
                        to = state.selectedEndDateMillis?.asInstant()
                            ?: state.selectedStartDateMillis?.asInstant()
                            ?: return@FullScreenDialogToolbar,
                    )
                    resultBackNavigator.navigateBack(result = results)
                }
            )
        }
    ) { padding ->
        DateRangePicker(
            state = state,
            modifier = Modifier
                .padding(padding)
                .testTag("date_range_picker"),
            title = null,
            headline = {
                Text(
                    text = when {
                        state.selectedStartDateMillis != null && state.selectedEndDateMillis != null -> {
                            val from =
                                state.selectedStartDateMillis?.asInstant() ?: return@DateRangePicker
                            val to =
                                state.selectedEndDateMillis?.asInstant() ?: return@DateRangePicker

                            stringResource(
                                id = StringResources.feed_filters_dates_value,
                                from.format(HUMAN_READABLE_DATE_FORMAT),
                                to.format(HUMAN_READABLE_DATE_FORMAT),
                            )
                        }

                        state.selectedStartDateMillis != null -> {
                            val from =
                                state.selectedStartDateMillis?.asInstant() ?: return@DateRangePicker
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
