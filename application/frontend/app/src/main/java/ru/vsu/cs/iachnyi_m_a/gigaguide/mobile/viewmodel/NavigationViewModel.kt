package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class NavigationViewModel: ViewModel() {
    var currentScreen = mutableStateOf(ScreenName.HOME)
    var showNavigationBar = currentScreen.value in listOf<ScreenName>(ScreenName.HOME, ScreenName.MAP,
        ScreenName.FAVORITE, ScreenName.SETTINGS)
}