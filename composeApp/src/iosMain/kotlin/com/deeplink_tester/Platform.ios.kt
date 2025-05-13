package com.deeplink_tester

import androidx.compose.runtime.Composable
import platform.Foundation.NSURL
import platform.Foundation.NSUserDefaults
import platform.UIKit.UIApplication

class IOSPlatformState : PlatformState {
    override fun launchDeeplink(deeplink: String) {

        NSURL.URLWithString(deeplink)?.let {
            if (UIApplication.sharedApplication().canOpenURL(it)) {
                UIApplication.sharedApplication.openURL(
                    url = it,
                    options = emptyMap<Any?, Any>(),
                    completionHandler = null
                )
            }
        }
    }

    override fun getPlatform(): String {
        return "iOS"
    }

    override fun isMobileBrowser(): Boolean {
        return false
    }

    override fun saveSettings(setting: String, key: String) {
        NSUserDefaults.standardUserDefaults.setObject(setting, key)
    }

    override fun getSettings(key: String): String? {
        return NSUserDefaults.standardUserDefaults.stringForKey(key)
    }
}

@Composable
actual fun rememberPlatformState(): PlatformState {
    return IOSPlatformState()
}