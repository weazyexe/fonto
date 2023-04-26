package dev.weazyexe.fonto.ui.features.settings.screens.colorpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyle
import dev.weazyexe.fonto.core.ui.components.BottomSheetLayout

@Destination(style = DestinationStyle.BottomSheet::class)
@Composable
fun ColorPickerDialog(
    args: ColorPickerArgs,
    navController: NavController
) {
    BottomSheetLayout(onBackClick = navController::navigateUp) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            items(count = args.colors.size) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(args.colors[it].data))
                )
            }
        }
    }
}