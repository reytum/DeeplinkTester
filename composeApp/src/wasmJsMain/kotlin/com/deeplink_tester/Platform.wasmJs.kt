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
}

@Composable
actual fun rememberPlatformState(): PlatformState {
    return WasmPlatformState()
}