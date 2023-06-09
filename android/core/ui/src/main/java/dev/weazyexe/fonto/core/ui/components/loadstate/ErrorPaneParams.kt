package dev.weazyexe.fonto.core.ui.components.loadstate

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource
import dev.weazyexe.fonto.common.data.ResponseError
import dev.weazyexe.fonto.core.ui.utils.StringResources

@Immutable
class ErrorPaneParams private constructor(
    val title: String,
    val message: String,
    val emoji: String,
    val action: Action? = null
) {

    @Immutable
    data class Action(
        @StringRes val title: Int,
        val onClick: () -> Unit
    )

    companion object {

        @Stable
        @Composable
        fun noInternet(action: Action? = null): ErrorPaneParams =
            ErrorPaneParams(
                title = stringResource(id = StringResources.error_no_internet_title),
                message = stringResource(id = StringResources.error_no_internet_message),
                emoji = "\uD83D\uDD0C",
                action = action
            )

        @Stable
        @Composable
        fun unknown(action: Action? = null) =
            ErrorPaneParams(
                title = stringResource(id = StringResources.error_unknown_title),
                message = stringResource(id = StringResources.error_unknown_message),
                emoji = "\uD83C\uDF43",
                action = action
            )

        @Stable
        @Composable
        fun empty(
            @StringRes title: Int = StringResources.error_empty_default_title,
            @StringRes message: Int = StringResources.error_empty_default_message,
            emoji: String = "\uD83D\uDCC2",
            action: Action? = null
        ) = ErrorPaneParams(
            title = stringResource(id = title),
            message = stringResource(id = message),
            emoji = emoji,
            action = action
        )

        @Stable
        @Composable
        fun emptyQuery(
            @StringRes title: Int = StringResources.error_empty_query_title,
            @StringRes message: Int = StringResources.error_empty_query_message,
            emoji: String = "\uD83D\uDD0D",
            action: Action? = null
        ) = ErrorPaneParams(
            title = stringResource(id = title),
            message = stringResource(id = message),
            emoji = emoji,
            action = action
        )

        @Stable
        @Composable
        fun notFound(
            @StringRes title: Int = StringResources.error_not_found_title,
            @StringRes message: Int = StringResources.error_not_found_message,
            emoji: String = "\uD83E\uDEB9",
            action: Action? = null
        ) = ErrorPaneParams(
            title = stringResource(id = title),
            message = stringResource(id = message),
            emoji = emoji,
            action = action
        )
    }
}

@Composable
fun ResponseError.asErrorPaneParams(action: ErrorPaneParams.Action? = null): ErrorPaneParams =
    if (this is ResponseError.NoInternetError) {
        ErrorPaneParams.noInternet(action)
    } else {
        ErrorPaneParams.unknown(action)
    }