package com.deeplink_tester.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

object ApiClient {
    suspend fun fetchDeepLinks(): String {
        try {
            val client = HttpClient()
            val response: HttpResponse =
                client.get("https://raw.githubusercontent.com/ashish-16-kotak/Neo-Deeplinks/refs/heads/main/deeplinks.json")
            client.close()
            return response.body<String>()
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }
}