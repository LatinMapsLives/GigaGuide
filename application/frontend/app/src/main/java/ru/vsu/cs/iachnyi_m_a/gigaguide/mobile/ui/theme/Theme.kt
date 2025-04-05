package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


private val LightColorScheme = lightColorScheme(
    primary = LightBlue,
    secondary = MediumBlue,

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

    background = DarkGrey,
    onPrimary = White,
    onSecondary = White,
    onBackground = White,

    onPrimaryContainer = LightGrey,

    error = Red

    )

@Composable
fun GigaGuideMobileTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}