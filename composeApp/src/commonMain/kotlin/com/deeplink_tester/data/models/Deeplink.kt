package com.deeplink_tester.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Deeplink(val name: String, val url: String)
