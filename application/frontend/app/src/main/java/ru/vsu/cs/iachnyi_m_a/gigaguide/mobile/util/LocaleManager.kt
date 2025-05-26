package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util

import android.content.res.Configuration
import android.os.LocaleList
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

import java.util.Locale

class LocaleManager{
    companion object {
        var currentLanguage by mutableStateOf("ru")
        var recomposeFlag by mutableStateOf(false)
    }
}

@Composable
fun RememberLocale(languageCode: String, recomposeCallback: () -> Unit) {

    val context = LocalContext.current
    val configuration = remember { Configuration() }
    val resources = remember { context.resources }
    val currentLocale =  resources.configuration.locales[0]

    if(currentLocale.language == languageCode) return
    DisposableEffect(languageCode) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        configuration.setLocales(LocaleList(locale))
        resources.updateConfiguration(configuration, resources.displayMetrics)
        Log.e("LANG", "REMEMBER " + languageCode)
        recomposeCallback.invoke()
        onDispose { }
    }
}