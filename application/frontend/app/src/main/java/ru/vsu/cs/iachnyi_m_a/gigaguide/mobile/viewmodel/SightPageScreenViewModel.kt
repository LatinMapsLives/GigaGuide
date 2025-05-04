package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.MomentRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import java.net.ConnectException
import java.net.SocketTimeoutException

@HiltViewModel
class SightPageScreenViewModel @Inject constructor(
    private val momentRepository: MomentRepository,
    private val sightRepository: SightRepository
) : ViewModel() {

    var sightId = -1L
    var sight = mutableStateOf<SightInfo?>(null)
    var momentNames = mutableStateListOf<String>()
    var loading = mutableStateOf<Boolean>(false)

    fun loadSight() {
        viewModelScope.launch {
            loading.value = true
            var loadedSight: SightInfo? = try {
                withContext(Dispatchers.IO) {
                    Log.e("sight", "requesting sight at $sightId")
                    sightRepository.getSightPageInfoById(sightId)
                }
            } catch (e: ConnectException) {
                null
            } catch (e: SocketTimeoutException) {
                null
            }
            sight.value = loadedSight
            Log.e("sight", sight.value.toString())
            momentNames.clear()
            var loadedMoments = try {
                withContext(Dispatchers.IO) {
                    momentRepository.getSightMoments(sightId)
                }
            } catch (e: ConnectException) {
                null
            } catch (e: SocketTimeoutException) {
                null
            }
            if (loadedMoments != null) {
                momentNames.addAll(loadedMoments.map { m -> m.name })
            }
            loading.value = false
        }
    }
}