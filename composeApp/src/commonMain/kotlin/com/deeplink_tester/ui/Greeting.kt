package com.deeplink_tester.ui


class Greeting {
    private val platform = "Android/iOS"

    fun greet(): String {
        return "Hello, ${platform}!"
    }
}