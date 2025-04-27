package com.deeplink_tester

import androidx.compose.runtime.Composable

interface PlatformState {
    fun launchDeeplink(deeplink: String)

    fun getPlatform(): String

    fun isMobileBrowser(): Boolean

    fun saveSettings(setting: String, key: String)

    fun getSettings(key: String): String?
}

@Composable
expect fun rememberPlatformState(): PlatformState