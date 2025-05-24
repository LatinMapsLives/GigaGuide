package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.SocketTimeoutException

class ServerUtils {
    companion object {
        const val SERVER_ADDRESS = "http://158.160.179.56:8080"
        fun imageLink(imageName: String): String {
            return "${SERVER_ADDRESS}/api/tour-sight/image?fileName=${imageName}"
        }
        fun audioGuideLink(momentId: Long): String{
            return "${SERVER_ADDRESS}/api/guide?id=${momentId}"
        }
        suspend fun <T> executeNetworkCall(block: suspend () -> T): T?{
            var result: T? = null
            withContext (Dispatchers.IO){
                result= try {
                    block.invoke()
                } catch (e: SocketTimeoutException){
                    Pancake.serverUnavailable()
                    null
                } catch (e: ConnectException) {
                    Pancake.noInternet()
                    null
                } catch (e: Exception) {
                    Log.e("ERR", e.stackTraceToString())
                    Pancake.serverError()
                    null
                }
            }
            return result
        }
    }
}