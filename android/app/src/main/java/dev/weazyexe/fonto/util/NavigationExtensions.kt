package dev.weazyexe.fonto.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.Direction
import dev.weazyexe.fonto.android.feature.feed.screens.destinations.FeedScreenDestination
import dev.weazyexe.fonto.android.feature.feed.screens.destinations.ManageFeedScreenDestination
import dev.weazyexe.navigation.Route

@Stable
@Composable
fun <D : DestinationSpec<*>, R> ResultRecipient<D, R>.handleResults(block: (R) -> Unit) {
    this.onNavResult {
        when (it) {
            is NavResult.Canceled -> {
                // Do nothing
            }

            is NavResult.Value -> {
                block(it.value)
            }
        }
    }
}

fun Route.asDirection(): Direction =
    when (this) {
        is Route.Feed -> asFeedDirection()
    }

private fun Route.Feed.asFeedDirection(): Direction =
    when (this) {
        Route.Feed.Root -> FeedScreenDestination
        Route.Feed.Manage -> ManageFeedScreenDestination
    }
