package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.lifecycle.ViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightTourThumbnail

class HomeViewModel : ViewModel() {
    var closestSights = mutableListOf<SightTourThumbnail>()
    var closestTours = mutableListOf<SightTourThumbnail>()
}