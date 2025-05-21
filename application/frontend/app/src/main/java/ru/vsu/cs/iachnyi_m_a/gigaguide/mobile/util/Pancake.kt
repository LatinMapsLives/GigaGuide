package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util

import android.content.Context
import android.util.Log
import kotlinx.coroutines.coroutineScope

class Pancake {
    companion object {
        private var onError: (String) -> Unit = {}
        private var onSuccess: (String) -> Unit = {}
        private var onInfo: (String) -> Unit = {}
        private var noInternetMessage: String = ""
        private var serverUnavailableMessage: String = ""
        private var serverErrorMessage: String = ""
        fun setMessageHandler(
            onError: (String) -> Unit,
            onSuccess: (String) -> Unit,
            onInfo: (String) -> Unit,
            noInternetMessage: String,
            serverUnavailableMessage: String,
            serverErrorMessage: String
        ) {
            this.onError = onError
            this.onSuccess = onSuccess
            this.onInfo = onInfo
            this.noInternetMessage = noInternetMessage
            this.serverUnavailableMessage = serverUnavailableMessage
            this.serverErrorMessage = serverErrorMessage
        }

        fun error(msg: String) {
            onError.invoke(msg)
        }

        fun info(msg: String) {
            onInfo.invoke(msg)
        }

        fun success(msg: String) {
            onSuccess.invoke(msg)
        }

        fun noInternet() {
            error(noInternetMessage)
        }

        fun serverUnavailable() {
            error(serverUnavailableMessage)
        }

        fun serverError() {
            error(serverErrorMessage)
        }
    }
}