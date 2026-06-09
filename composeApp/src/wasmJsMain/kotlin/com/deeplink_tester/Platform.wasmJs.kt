package com.deeplink_tester

import androidx.compose.runtime.Composable
import kotlinx.browser.window

class WasmPlatformState : PlatformState {
    override fun launchDeeplink(deeplink: String) {
        if (isMobileBrowser()) {
            // On mobile browsers, navigate the current window directly.
            // This allows the OS to intercept the navigation and open the
            // respective native app via Universal Links (iOS) or App Links (Android).
            window.location.href = deeplink
        } else {
            // On desktop browsers, open in a new tab directly with the URL.
            // Using window.open(url) instead of opening blank then setting href
            // ensures proper handling of the link.
            window.open(deeplink, "_blank")
        }
    }

    override fun getPlatform(): String {
        return "Web"
    }

    override fun saveSettings(setting: String, key: String) {
        window.localStorage.setItem(key, setting)
    }

    override fun getSettings(key: String): String? {
        return window.localStorage.getItem(key)
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

    private fun getBrowserPlatform(): String {
        val userAgent = window.navigator.userAgent
        if (userAgent.contains("iPhone", ignoreCase = true)) {
            return "iPhone"
        }
        if (userAgent.contains("iPad", ignoreCase = true)) {
            return "iPad"
        }
        if (userAgent.contains("Android", ignoreCase = true)) {
            return "Android"
        }
        if (userAgent.contains("Mobile", ignoreCase = true)) {
            return "Mobile"
        }
        return "Web"
    }
}

@Composable
actual fun rememberPlatformState(): PlatformState {
    return WasmPlatformState()
}