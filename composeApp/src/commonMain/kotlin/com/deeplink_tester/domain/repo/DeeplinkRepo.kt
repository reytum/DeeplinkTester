package com.deeplink_tester.domain.repo

import com.deeplink_tester.PlatformState
import com.deeplink_tester.data.deeplinkJson
import com.deeplink_tester.data.models.Category
import com.deeplink_tester.data.models.Deeplink
import com.deeplink_tester.data.network.ApiClient
import kotlinx.serialization.json.Json

object DeeplinkRepo {
    private const val LOCAL_DEEPLINK = "localDeepLinks"

    fun getInitialDeepLinks(): List<Category> {
        try {
            val map = Json.decodeFromString<Map<String, List<Deeplink>>>(deeplinkJson)
            return map.map { (categoryName, deepLinks) ->
                Category(
                    name = categoryName,
                    deepLinks = deepLinks
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return listOf()
    }

    fun getLocalDeepLinks(platformState: PlatformState): List<Category> {
        val deeplinkString = platformState.getSettings(LOCAL_DEEPLINK) ?: deeplinkJson
        return try {
            val map = Json.decodeFromString<Map<String, List<Deeplink>>>(deeplinkString)
            map.map { (categoryName, deepLinks) ->
                Category(
                    name = categoryName,
                    deepLinks = deepLinks
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun fetchDeepLinks(platformState: PlatformState): List<Category> {
        val deeplinkString = ApiClient.fetchDeepLinks()
        if (deeplinkString.isNotEmpty()) {
            try {
                val deeplinkMap = Json.decodeFromString<Map<String, List<Deeplink>>>(deeplinkString)
                platformState.saveSettings(LOCAL_DEEPLINK, deeplinkString)
                return deeplinkMap.map { (categoryName, deepLinks) ->
                    Category(
                        name = categoryName,
                        deepLinks = deepLinks
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return emptyList()
    }
}