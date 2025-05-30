package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.CurrentThemeSettings
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ThemeSettings


private val LightColorScheme = lightColorScheme(
    primary = LightBlue,
    secondary = MediumBlue,
    tertiary = TertiaryLight,

    background = White,
    onPrimary = White,
    onSecondary = White,
    onBackground = Black,

    onPrimaryContainer = MediumGrey,

    error = Red
)

private val DarkColorScheme = darkColorScheme(
    primary = MediumBlue,
    secondary = DarkBlue,
    tertiary = TertiaryDark,

    background = DarkGrey,
    onPrimary = White,
    onSecondary = White,
    onBackground = White,

    onPrimaryContainer = LightGrey,

    error = Red
)

@Composable
fun GigaGuideMobileTheme(
    darkTheme: Boolean = CurrentThemeSettings.isAppInDarkTheme(),
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}