package com.deeplink_tester.data.network

import com.deeplink_tester.data.models.Deeplink
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json

object ApiClient {
    suspend fun fetchDeepLinks(): Map<String, List<Deeplink>> {
        try {
            val client = HttpClient()
            val response: HttpResponse =
                client.get("https://raw.githubusercontent.com/ashish-16-kotak/Neo-Deeplinks/refs/heads/main/deeplinks.json")
            client.close()
            return Json.decodeFromString<Map<String, List<Deeplink>>>(response.body<String>())
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyMap()
        }
    }
}