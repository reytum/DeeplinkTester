package com.deeplink_tester.domain.repo

import com.deeplink_tester.data.deeplinkJson
import com.deeplink_tester.data.models.Category
import com.deeplink_tester.data.models.Deeplink
import com.deeplink_tester.data.network.ApiClient
import kotlinx.serialization.json.Json

object DeeplinkRepo {
    fun getInitialDeepLinks(): List<Category> {
        val map = Json.decodeFromString<Map<String, List<Deeplink>>>(deeplinkJson)
        return map.map { (categoryName, deepLinks) ->
            Category(
                name = categoryName,
                deepLinks = deepLinks
            )
        }
    }

    suspend fun fetchDeepLinks(): List<Category> {
        val deeplinkMap = ApiClient.fetchDeepLinks()
        return deeplinkMap.map { (categoryName, deepLinks) ->
            Category(
                name = categoryName,
                deepLinks = deepLinks
            )
        }
    }
}