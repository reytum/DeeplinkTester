package com.deeplink_tester.data.models

data class BaseUrl(val name: String, val url: String) {
    fun isLocalhost(): Boolean {
        return name == "Localhost"
    }
}
