package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.lifecycle.ViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R

class NavigationBarViewModel: ViewModel() {
    val iconIds = listOf<Int>(R.drawable.home, R.drawable.map, R.drawable.bookmark, R.drawable.person)
    val screenLinks = listOf<ScreenName>(ScreenName.HOME, ScreenName.MAP, ScreenName.FAVORITE,
        ScreenName.SETTINGS)
    val iconLabels = listOf<Int>(R.string.nav_label_home, R.string.nav_label_map, R.string.nav_label_favorite, R.string.nav_label_profile)
}