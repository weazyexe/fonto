package dev.weazyexe.fonto.ui.features.feed.screens.categories

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.core.ui.components.AnimatedAppearing
import dev.weazyexe.fonto.core.ui.components.ArrowBack
import dev.weazyexe.fonto.core.ui.components.loadstate.ErrorPane
import dev.weazyexe.fonto.core.ui.components.loadstate.ErrorPaneParams
import dev.weazyexe.fonto.core.ui.components.loadstate.LoadStateComponent
import dev.weazyexe.fonto.core.ui.components.loadstate.LoadingPane
import dev.weazyexe.fonto.core.ui.components.loadstate.asErrorPaneParams
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.theme.ThemedPreview
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.ui.features.feed.components.category.CategoryItem
import dev.weazyexe.fonto.ui.features.feed.components.category.CategoryViewState
import dev.weazyexe.fonto.ui.features.feed.preview.CategoryViewStatePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesBody(
    categoriesLoadState: LoadState<List<CategoryViewState>>,
    snackbarHostState: SnackbarHostState,
    onCategoryClick: (CategoryViewState) -> Unit,
    onDeleteClick: (CategoryViewState) -> Unit,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .imePadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = StringResources.categories_title)) },
                navigationIcon = { ArrowBack(onBackClick) },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            AnimatedAppearing {
                FloatingActionButton(
                    onClick = onAddClick
                ) {
                    Icon(
                        painter = painterResource(id = DrawableResources.ic_add_24),
                        contentDescription = null
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        LoadStateComponent(
            loadState = categoriesLoadState,
            onSuccess = {
                CategoriesList(
                    list = it,
                    padding = padding,
                    onClick = onCategoryClick,
                    onDeleteClick = onDeleteClick
                )
            },
            onError = {
                ErrorPane(it.error.asErrorPaneParams())
            },
            onLoading = { LoadingPane() }
        )
    }
}

@Composable
private fun CategoriesList(
    list: List<CategoryViewState>,
    padding: PaddingValues,
    onClick: (CategoryViewState) -> Unit,
    onDeleteClick: (CategoryViewState) -> Unit
) {
    val layoutDirection = LocalLayoutDirection.current
    val calculatedPaddings = PaddingValues(
        start = padding.calculateStartPadding(layoutDirection),
        top = padding.calculateTopPadding(),
        end = padding.calculateEndPadding(layoutDirection),
        bottom = padding.calculateBottomPadding() + 56.dp
    )

    if (list.isNotEmpty()) {
        LazyColumn(contentPadding = calculatedPaddings) {
            itemsIndexed(
                items = list,
                key = { _, item -> item.id.origin }
            ) { index, item ->
                CategoryItem(
                    category = item,
                    onClick = { onClick(item) },
                    onDeleteClick = { onDeleteClick(item) },
                    modifier = Modifier.fillMaxWidth()
                )

                if (index != list.size - 1) {
                    Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                }
            }
        }
    } else {
        ErrorPane(params = ErrorPaneParams.empty())
    }
}

@Preview
@Composable
private fun CategoriesBodyPreview() = ThemedPreview {
    CategoriesBody(
        categoriesLoadState = LoadState.Data(
            listOf(
                CategoryViewStatePreview.default,
                CategoryViewStatePreview.noFeeds,
            )
        ),
        snackbarHostState = SnackbarHostState(),
        onAddClick = {},
        onBackClick = {},
        onCategoryClick = {},
        onDeleteClick = {}
    )
}