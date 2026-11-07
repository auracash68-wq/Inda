package com.example.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val CashIndiaLightColorScheme = lightColorScheme(
    primary = PrimaryNavy,
    onPrimary = PureWhite,
    secondary = SaffronOrange,
    onSecondary = PureWhite,
    tertiary = PrimaryGreen,
    onTertiary = PureWhite,
    background = MainScreenBackground,
    onBackground = PrimaryNavy,
    surface = CardBackground,
    onSurface = PrimaryNavy,
    surfaceVariant = LightSurface,
    onSurfaceVariant = SecondaryText,
    outline = CardBorderColor
)

@Composable
fun CashIndiaTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = MainScreenBackground.toArgb()
            window.navigationBarColor = PureWhite.toArgb()
            val controller = WindowCompat.getInsetsController(window, view)
            controller.isAppearanceLightStatusBars = true
            controller.isAppearanceLightNavigationBars = true
        }
    }

    MaterialTheme(
        colorScheme = CashIndiaLightColorScheme,
        typography = Typography,
        content = content
    )
}
