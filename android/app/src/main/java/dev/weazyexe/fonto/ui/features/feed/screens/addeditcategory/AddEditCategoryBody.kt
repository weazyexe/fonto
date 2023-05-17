package dev.weazyexe.fonto.ui.features.feed.screens.addeditcategory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.components.toolbar.FullScreenDialogToolbar

@Composable
fun AddEditCategoryBody(
    isEditMode: Boolean,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Scaffold(
        topBar = {
            FullScreenDialogToolbar(
                title = stringResource(
                    id = if (isEditMode) {
                        R.string.add_edit_category_edit
                    } else {
                        R.string.add_edit_category_new
                    }
                ),
                doneButtonText = stringResource(
                    if (isEditMode) {
                        R.string.add_edit_category_save
                    } else {
                        R.string.add_edit_category_create
                    }
                ),
                onBackClick = onBackClick,
                onDoneClick = onSaveClick
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Add/edit category")
        }
    }
}