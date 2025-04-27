package com.deeplink_tester

import androidx.compose.runtime.Composable
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

class IOSPlatformState : PlatformState {
    override fun launchDeeplink(deeplink: String) {
        NSURL.URLWithString(deeplink)?.let {
            if (UIApplication.sharedApplication().canOpenURL(it)) {
                UIApplication.sharedApplication().openURL(url = it)
            }
        }
    }

    override fun getPlatform(): String {
        return "iOS"
    }
}

@Composable
actual fun rememberPlatformState(): PlatformState {
    return IOSPlatformState()
}