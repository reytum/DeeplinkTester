package com.deeplink_tester

import androidx.compose.runtime.Composable
import kotlinx.browser.window

class WasmPlatformState : PlatformState {
    override fun launchDeeplink(deeplink: String) {
        window.open(deeplink)
    }

    override fun getPlatform(): String {
        return "Web"
    }

    override fun isMobileBrowser(): Boolean {
        val userAgent = window.navigator.userAgent
        return userAgent.contains("Android", ignoreCase = true) || userAgent.contains(
            "iPhone",
            ignoreCase = true
        ) ||
                userAgent.contains("iPad", ignoreCase = true) || userAgent.contains(
            "Mobile",
            ignoreCase = true
        )
    }
}

@Composable
actual fun rememberPlatformState(): PlatformState {
    return WasmPlatformState()
}