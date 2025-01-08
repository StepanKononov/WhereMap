package com.north.wheremap.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = _root_ide_package_.com.north.wheremap.core.ui.theme.primaryDark,
    onPrimary = _root_ide_package_.com.north.wheremap.core.ui.theme.onPrimaryDark,
    primaryContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.primaryContainerDark,
    onPrimaryContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.onPrimaryContainerDark,
    secondary = _root_ide_package_.com.north.wheremap.core.ui.theme.secondaryDark,
    onSecondary = _root_ide_package_.com.north.wheremap.core.ui.theme.onSecondaryDark,
    secondaryContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.secondaryContainerDark,
    onSecondaryContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.onSecondaryContainerDark,
    tertiary = _root_ide_package_.com.north.wheremap.core.ui.theme.tertiaryDark,
    onTertiary = _root_ide_package_.com.north.wheremap.core.ui.theme.onTertiaryDark,
    tertiaryContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.tertiaryContainerDark,
    onTertiaryContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.onTertiaryContainerDark,
    error = _root_ide_package_.com.north.wheremap.core.ui.theme.errorDark,
    onError = _root_ide_package_.com.north.wheremap.core.ui.theme.onErrorDark,
    errorContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.errorContainerDark,
    onErrorContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.onErrorContainerDark,
    background = _root_ide_package_.com.north.wheremap.core.ui.theme.backgroundDark,
    onBackground = _root_ide_package_.com.north.wheremap.core.ui.theme.onBackgroundDark,
    surface = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceDark,
    onSurface = _root_ide_package_.com.north.wheremap.core.ui.theme.onSurfaceDark,
    surfaceVariant = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceVariantDark,
    onSurfaceVariant = _root_ide_package_.com.north.wheremap.core.ui.theme.onSurfaceVariantDark,
    outline = _root_ide_package_.com.north.wheremap.core.ui.theme.outlineDark,
    outlineVariant = _root_ide_package_.com.north.wheremap.core.ui.theme.outlineVariantDark,
    scrim = _root_ide_package_.com.north.wheremap.core.ui.theme.scrimDark,
    inverseSurface = _root_ide_package_.com.north.wheremap.core.ui.theme.inverseSurfaceDark,
    inverseOnSurface = _root_ide_package_.com.north.wheremap.core.ui.theme.inverseOnSurfaceDark,
    inversePrimary = _root_ide_package_.com.north.wheremap.core.ui.theme.inversePrimaryDark,
    surfaceDim = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceDimDark,
    surfaceBright = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceBrightDark,
    surfaceContainerLowest = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerLowestDark,
    surfaceContainerLow = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerLowDark,
    surfaceContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerDark,
    surfaceContainerHigh = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerHighDark,
    surfaceContainerHighest = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = _root_ide_package_.com.north.wheremap.core.ui.theme.errorLightHighContrast,
    onError = _root_ide_package_.com.north.wheremap.core.ui.theme.onErrorLightHighContrast,
    errorContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.errorContainerLightHighContrast,
    onErrorContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.onErrorContainerLightHighContrast,
    background = _root_ide_package_.com.north.wheremap.core.ui.theme.backgroundLightHighContrast,
    onBackground = _root_ide_package_.com.north.wheremap.core.ui.theme.onBackgroundLightHighContrast,
    surface = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceLightHighContrast,
    onSurface = _root_ide_package_.com.north.wheremap.core.ui.theme.onSurfaceLightHighContrast,
    surfaceVariant = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceVariantLightHighContrast,
    onSurfaceVariant = _root_ide_package_.com.north.wheremap.core.ui.theme.onSurfaceVariantLightHighContrast,
    outline = _root_ide_package_.com.north.wheremap.core.ui.theme.outlineLightHighContrast,
    outlineVariant = _root_ide_package_.com.north.wheremap.core.ui.theme.outlineVariantLightHighContrast,
    scrim = _root_ide_package_.com.north.wheremap.core.ui.theme.scrimLightHighContrast,
    inverseSurface = _root_ide_package_.com.north.wheremap.core.ui.theme.inverseSurfaceLightHighContrast,
    inverseOnSurface = _root_ide_package_.com.north.wheremap.core.ui.theme.inverseOnSurfaceLightHighContrast,
    inversePrimary = _root_ide_package_.com.north.wheremap.core.ui.theme.inversePrimaryLightHighContrast,
    surfaceDim = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceDimLightHighContrast,
    surfaceBright = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceBrightLightHighContrast,
    surfaceContainerLowest = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerLowLightHighContrast,
    surfaceContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerLightHighContrast,
    surfaceContainerHigh = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = _root_ide_package_.com.north.wheremap.core.ui.theme.primaryDarkMediumContrast,
    onPrimary = _root_ide_package_.com.north.wheremap.core.ui.theme.onPrimaryDarkMediumContrast,
    primaryContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.primaryContainerDarkMediumContrast,
    onPrimaryContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.onPrimaryContainerDarkMediumContrast,
    secondary = _root_ide_package_.com.north.wheremap.core.ui.theme.secondaryDarkMediumContrast,
    onSecondary = _root_ide_package_.com.north.wheremap.core.ui.theme.onSecondaryDarkMediumContrast,
    secondaryContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.secondaryContainerDarkMediumContrast,
    onSecondaryContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.onSecondaryContainerDarkMediumContrast,
    tertiary = _root_ide_package_.com.north.wheremap.core.ui.theme.tertiaryDarkMediumContrast,
    onTertiary = _root_ide_package_.com.north.wheremap.core.ui.theme.onTertiaryDarkMediumContrast,
    tertiaryContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.onTertiaryContainerDarkMediumContrast,
    error = _root_ide_package_.com.north.wheremap.core.ui.theme.errorDarkMediumContrast,
    onError = _root_ide_package_.com.north.wheremap.core.ui.theme.onErrorDarkMediumContrast,
    errorContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.errorContainerDarkMediumContrast,
    onErrorContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.onErrorContainerDarkMediumContrast,
    background = _root_ide_package_.com.north.wheremap.core.ui.theme.backgroundDarkMediumContrast,
    onBackground = _root_ide_package_.com.north.wheremap.core.ui.theme.onBackgroundDarkMediumContrast,
    surface = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceDarkMediumContrast,
    onSurface = _root_ide_package_.com.north.wheremap.core.ui.theme.onSurfaceDarkMediumContrast,
    surfaceVariant = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceVariantDarkMediumContrast,
    onSurfaceVariant = _root_ide_package_.com.north.wheremap.core.ui.theme.onSurfaceVariantDarkMediumContrast,
    outline = _root_ide_package_.com.north.wheremap.core.ui.theme.outlineDarkMediumContrast,
    outlineVariant = _root_ide_package_.com.north.wheremap.core.ui.theme.outlineVariantDarkMediumContrast,
    scrim = _root_ide_package_.com.north.wheremap.core.ui.theme.scrimDarkMediumContrast,
    inverseSurface = _root_ide_package_.com.north.wheremap.core.ui.theme.inverseSurfaceDarkMediumContrast,
    inverseOnSurface = _root_ide_package_.com.north.wheremap.core.ui.theme.inverseOnSurfaceDarkMediumContrast,
    inversePrimary = _root_ide_package_.com.north.wheremap.core.ui.theme.inversePrimaryDarkMediumContrast,
    surfaceDim = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceDimDarkMediumContrast,
    surfaceBright = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerLowDarkMediumContrast,
    surfaceContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = _root_ide_package_.com.north.wheremap.core.ui.theme.primaryDarkHighContrast,
    onPrimary = _root_ide_package_.com.north.wheremap.core.ui.theme.onPrimaryDarkHighContrast,
    primaryContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.primaryContainerDarkHighContrast,
    onPrimaryContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.onPrimaryContainerDarkHighContrast,
    secondary = _root_ide_package_.com.north.wheremap.core.ui.theme.secondaryDarkHighContrast,
    onSecondary = _root_ide_package_.com.north.wheremap.core.ui.theme.onSecondaryDarkHighContrast,
    secondaryContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.secondaryContainerDarkHighContrast,
    onSecondaryContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.onSecondaryContainerDarkHighContrast,
    tertiary = _root_ide_package_.com.north.wheremap.core.ui.theme.tertiaryDarkHighContrast,
    onTertiary = _root_ide_package_.com.north.wheremap.core.ui.theme.onTertiaryDarkHighContrast,
    tertiaryContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.tertiaryContainerDarkHighContrast,
    onTertiaryContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.onTertiaryContainerDarkHighContrast,
    error = _root_ide_package_.com.north.wheremap.core.ui.theme.errorDarkHighContrast,
    onError = _root_ide_package_.com.north.wheremap.core.ui.theme.onErrorDarkHighContrast,
    errorContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.errorContainerDarkHighContrast,
    onErrorContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.onErrorContainerDarkHighContrast,
    background = _root_ide_package_.com.north.wheremap.core.ui.theme.backgroundDarkHighContrast,
    onBackground = _root_ide_package_.com.north.wheremap.core.ui.theme.onBackgroundDarkHighContrast,
    surface = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceDarkHighContrast,
    onSurface = _root_ide_package_.com.north.wheremap.core.ui.theme.onSurfaceDarkHighContrast,
    surfaceVariant = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceVariantDarkHighContrast,
    onSurfaceVariant = _root_ide_package_.com.north.wheremap.core.ui.theme.onSurfaceVariantDarkHighContrast,
    outline = _root_ide_package_.com.north.wheremap.core.ui.theme.outlineDarkHighContrast,
    outlineVariant = _root_ide_package_.com.north.wheremap.core.ui.theme.outlineVariantDarkHighContrast,
    scrim = _root_ide_package_.com.north.wheremap.core.ui.theme.scrimDarkHighContrast,
    inverseSurface = _root_ide_package_.com.north.wheremap.core.ui.theme.inverseSurfaceDarkHighContrast,
    inverseOnSurface = _root_ide_package_.com.north.wheremap.core.ui.theme.inverseOnSurfaceDarkHighContrast,
    inversePrimary = _root_ide_package_.com.north.wheremap.core.ui.theme.inversePrimaryDarkHighContrast,
    surfaceDim = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceDimDarkHighContrast,
    surfaceBright = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceBrightDarkHighContrast,
    surfaceContainerLowest = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerLowDarkHighContrast,
    surfaceContainer = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerDarkHighContrast,
    surfaceContainerHigh = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = _root_ide_package_.com.north.wheremap.core.ui.theme.surfaceContainerHighestDarkHighContrast,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun WhereMapTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

