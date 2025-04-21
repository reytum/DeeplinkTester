package com.deeplink_tester.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val name: String,
    val deepLinks: List<Deeplink>,
)
