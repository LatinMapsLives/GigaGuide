package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class ThemeSettings{
    AS_DEVICE,
    ALWAYS_LIGHT,
    ALWAYS_DARK
}

class CurrentThemeSettings {
    companion object{
        var currentState by mutableStateOf(ThemeSettings.AS_DEVICE)

        @Composable
        fun isAppInDarkTheme(): Boolean{
            return if (currentState == ThemeSettings.AS_DEVICE) isSystemInDarkTheme() else currentState == ThemeSettings.ALWAYS_DARK
        }
    }
}