package dev.weazyexe.fonto.ui.features.settings.screens.colorpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.components.BottomSheetLayout

@Destination(style = DestinationStyle.BottomSheet::class)
@Composable
fun ColorPickerDialog(
    args: ColorPickerArgs,
    navController: NavController,
    resultBackNavigator: ResultBackNavigator<Long>
) {
    BottomSheetLayout(onBackClick = navController::navigateUp) {
        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = stringResource(id = R.string.color_picker_pick_color),
            modifier = Modifier.padding(start = 16.dp),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.size(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(count = args.colors.size) {
                val color = Color(args.colors[it].data)
                Box(contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(color)
                            .clickable { resultBackNavigator.navigateBack(result = args.colors[it].data) }
                            .then(
                                if (args.colors[it].data == args.selectedColor.data) {
                                    Modifier
                                        .border(width = 1.dp, color = color, shape = CircleShape)
                                        .border(
                                            width = 6.dp,
                                            color = MaterialTheme.colorScheme.surface,
                                            shape = CircleShape
                                        )
                                } else {
                                    Modifier
                                }
                            )
                    )
                }
            }
        }
    }
}