package dev.weazyexe.fonto.core.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import com.google.android.material.color.utilities.Scheme

object ThemeGenerator {

    @SuppressLint("RestrictedApi")
    fun generate(color: Int, isDark: Boolean): ColorScheme {
        val scheme = if (isDark) {
            Scheme.dark(color)
        } else {
            Scheme.light(color)
        }

        return ColorScheme(
            primary = Color(scheme.primary),
            primaryContainer = Color(scheme.primaryContainer),
            secondary = Color(scheme.secondary),
            secondaryContainer = Color(scheme.secondaryContainer),
            tertiary = Color(scheme.tertiary),
            tertiaryContainer = Color(scheme.tertiaryContainer),
            onPrimary = Color(scheme.onPrimary),
            onPrimaryContainer = Color(scheme.onPrimaryContainer),
            onSecondary = Color(scheme.onSecondary),
            onSecondaryContainer = Color(scheme.onSecondaryContainer),
            onTertiary = Color(scheme.onTertiary),
            onTertiaryContainer = Color(scheme.onTertiaryContainer),
            background = Color(scheme.background),
            onBackground = Color(scheme.onBackground),
            inversePrimary = Color(scheme.inversePrimary),
            surface = Color(scheme.surface),
            onSurface = Color(scheme.onSurface),
            surfaceVariant = Color(scheme.surfaceVariant),
            onSurfaceVariant = Color(scheme.onSurfaceVariant),
            surfaceTint = Color(scheme.primary), // no mdc implementation
            inverseSurface = Color(scheme.inverseSurface),
            inverseOnSurface = Color(scheme.inverseOnSurface),
            error = Color(scheme.error),
            onError = Color(scheme.onError),
            errorContainer = Color(scheme.errorContainer),
            onErrorContainer = Color(scheme.onErrorContainer),
            outline = Color(scheme.outline),
            outlineVariant = Color(scheme.outlineVariant),
            scrim = Color(scheme.scrim)
        )
    }
}