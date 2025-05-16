package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile

class ServerUtils {
    companion object {
        const val SERVER_ADDRESS = "http://192.168.1.84:8080"
        fun imageLink(imageName: String): String {
            return "${SERVER_ADDRESS}/api/tour-sight/image?fileName=${imageName}"
        }
        fun audioGuideLink(momentId: Long): String{
            return "${SERVER_ADDRESS}/api/guide?id=${momentId}"
        }
    }
}