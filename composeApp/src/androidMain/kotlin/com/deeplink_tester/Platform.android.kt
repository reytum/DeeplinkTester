package com.deeplink_tester

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager.getDefaultSharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.core.content.edit

@Stable
internal class AndroidPlatformState(private val context: Context) : PlatformState {
    override fun launchDeeplink(deeplink: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, deeplink.toUri()))
    }

    override fun getPlatform(): String {
        return "Android"
    }

    override fun isMobileBrowser(): Boolean {
        return false
    }

    override fun saveSettings(setting: String, key: String) {
        getDefaultSharedPreferences(context).edit { putString(key, setting) }
    }

    override fun getSettings(key: String): String? {
        return getDefaultSharedPreferences(context).getString(key, null)
    }
}

@Composable
actual fun rememberPlatformState(): PlatformState {
    val context = LocalContext.current
    val state = AndroidPlatformState(context)
    return state
}