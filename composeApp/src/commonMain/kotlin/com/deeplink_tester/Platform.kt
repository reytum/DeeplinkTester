package com.deeplink_tester

import androidx.compose.runtime.Composable

interface PlatformState {
    fun launchDeeplink(deeplink: String)

    fun getPlatform(): String
}

@Composable
expect fun rememberPlatformState(): PlatformState