@file:OptIn(ExperimentalMaterial3Api::class)

package dev.weazyexe.fonto.core.ui.components.filters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.common.feature.filter.Bool
import dev.weazyexe.fonto.common.feature.filter.Single
import dev.weazyexe.fonto.core.ui.R

@Composable
fun FiltersRow(
    filters: List<FilterViewState<*>>,
    onBoolFilterChange: (Bool<*>) -> Unit,
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
                        selected = it.filter.value,
                        onClick = { onBoolFilterChange(it.filter.toggle()) },
                        label = { Text(text = it.title) },
                        leadingIcon = {
                            AnimatedVisibility(
                                visible = it.filter.value,
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

                is Single<*> -> {
                    // TODO implement other filter chips
                }

                else -> {
                    // Do nothing
                }
            }
        }
    }
}