package com.deeplink_tester

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

@Stable
internal class AndroidPlatformState(private val context: Context) : PlatformState {
    override fun launchDeeplink(deeplink: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, deeplink.toUri()))
    }

    override fun getPlatform(): String {
        return "Android"
    }
}

@Composable
actual fun rememberPlatformState(): PlatformState {
    val context = LocalContext.current
    val state = AndroidPlatformState(context)
    return state
}