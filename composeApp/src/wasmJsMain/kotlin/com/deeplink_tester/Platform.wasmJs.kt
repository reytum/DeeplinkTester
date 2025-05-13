package com.deeplink_tester

import androidx.compose.runtime.Composable
import kotlinx.browser.window

class WasmPlatformState : PlatformState {
    override fun launchDeeplink(deeplink: String) {
        val openedWindow = window.open("")?.apply { location.href = deeplink }
        val platform = getBrowserPlatform()
/*
        if ((platform == "iPhone" || platform == "iPad" || platform == "Web") && (openedWindow?.location?.href == null || openedWindow.location.href == "about:blank")) {
            openedWindow?.alert(openedWindow.location.toString())
        }*/
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