@file:OptIn(ExperimentalMaterial3Api::class)

package dev.weazyexe.fonto.core.ui.components.filters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.common.feature.filter.Bool
import dev.weazyexe.fonto.common.feature.filter.Dates
import dev.weazyexe.fonto.common.feature.filter.Multiple
import dev.weazyexe.fonto.core.ui.R

@Composable
fun FiltersRow(
    filters: List<FilterViewState<*>>,
    onBoolFilterChange: (Bool<*>) -> Unit,
    openDateRangePickerDialog: (Dates<*>) -> Unit,
    onClearDatesFilter: (Dates<*>) -> Unit,
    openMultiplePickerDialog: (Multiple<*, *>) -> Unit,
    onClearMultipleFilter: (Multiple<*, *>) -> Unit,
    modifier: Modifier = Modifier,
    startPadding: Dp = 16.dp
) {
    val scrollState = rememberScrollState()
    Row(
        modifier = modifier.horizontalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.size(startPadding))

        filters.forEach {
            when (it.filter) {
                is Bool<*> -> {
                    FilterChip(
                        selected = it.filter.isEnabled,
                        onClick = { onBoolFilterChange(it.filter.toggle()) },
                        label = { Text(text = it.title) },
                        modifier = Modifier.padding(end = 8.dp),
                        leadingIcon = {
                            AnimatedVisibility(
                                visible = it.filter.isEnabled,
                                enter = fadeIn() + expandHorizontally(),
                                exit = fadeOut() + shrinkHorizontally()
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_done_24),
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }

                is Dates<*> -> {
                    FilterChip(
                        selected = it.filter.range != null,
                        onClick = { openDateRangePickerDialog(it.filter) },
                        label = { Text(text = it.title) },
                        modifier = Modifier.padding(end = 8.dp),
                        leadingIcon = {
                            CloseButton(isVisible = it.filter.range != null) {
                                onClearDatesFilter(it.filter.change(null))
                            }
                        }
                    )
                }

                is Multiple<*, *> -> {
                    FilterChip(
                        selected = it.filter.values.isNotEmpty(),
                        onClick = { openMultiplePickerDialog(it.filter) },
                        label = { Text(text = it.title) },
                        modifier = Modifier.padding(end = 8.dp),
                        leadingIcon = {
                            CloseButton(isVisible = it.filter.values.isNotEmpty()) {
                                onClearMultipleFilter(
                                    (it.filter as Multiple<Any, *>).change(
                                        emptyList(),
                                        it.filter.possibleValues
                                    )
                                )
                            }
                        }
                    )
                }

                else -> {
                    // Do nothing
                }
            }
        }
    }
}

@Composable
private fun CloseButton(
    isVisible: Boolean,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + expandHorizontally(),
        exit = fadeOut() + shrinkHorizontally()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_close_24),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = onClick)
        )
    }
}