package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.review.Review
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.review.ReviewRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.review.SightReviewRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.review.TourReviewRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils

@HiltViewModel
class ReviewScreenViewModel @Inject constructor(
    private val sightReviewRepository: SightReviewRepository,
    private val tourReviewRepository: TourReviewRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    var objectId = -1
    var isTour = false
    var myReview by mutableStateOf<Review?>(null)
    var otherReviews = mutableStateListOf<Review>()
    var editorIsOpen by mutableStateOf(false)
    var loadingComments by mutableStateOf(false)
    var sendingComment by mutableStateOf(false)
    var authorized by mutableStateOf(false)
    var emptyCommentError by mutableStateOf(false)

    var newCommentText by mutableStateOf("")
    var newCommentRating by mutableIntStateOf(5)

    fun loadReviews() {
        loadingComments = true
        var repository: ReviewRepository =
            if (isTour) tourReviewRepository else sightReviewRepository
        viewModelScope.launch {
            var token = dataStoreManager.getJWT()
            authorized = token != null
            var reviews =
                ServerUtils.executeNetworkCall { repository.getAllReviews(token ?: "", objectId) }
            if (reviews != null) {
                otherReviews.clear()
                myReview = reviews.myReview
                otherReviews.addAll(reviews.otherReviews)
            }
            loadingComments = false
        }
    }

    fun deleteReview() {
        loadingComments = true
        viewModelScope.launch {
            var repository: ReviewRepository =
                if (isTour) tourReviewRepository else sightReviewRepository
            if (dataStoreManager.getJWT() == null) return@launch
            var success =
                ServerUtils.executeNetworkCall {
                    repository.deleteReview(
                        token = dataStoreManager.getJWT()!!,
                        id = myReview!!.id
                    )
                }
            if(success == null) {
                sendingComment = false
            } else {
                loadReviews()
                sendingComment = false
            }
        }
    }

    fun postReview() {
        if (newCommentText.trim().isEmpty()) {
            emptyCommentError = true
        } else {
            var repository: ReviewRepository =
                if (isTour) tourReviewRepository else sightReviewRepository
            viewModelScope.launch {
                if (dataStoreManager.getJWT() == null) return@launch
                sendingComment = true
                var success =
                    ServerUtils.executeNetworkCall {
                        repository.addReview(
                            token = dataStoreManager.getJWT()!!,
                            rating = newCommentRating,
                            comment = newCommentText,
                            objectId = objectId.toLong()
                        )
                    }
                if(success == null) {
                     sendingComment = false
                } else {
                    if(success){
                        loadReviews()
                        editorIsOpen = false
                        newCommentText = ""
                        newCommentRating = 5
                    } else {

                    }
                    sendingComment = false
                }
            }

        }
    }
}