package com.cmedina.sports.shared.network

import com.cmedina.sports.shared.entity.TeamResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.serialization.json.Json

class SportsApi {

    private val httpClient = HttpClient{
        install(JsonFeature) {
            val json = Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }

    suspend fun getAllTeams() : TeamResponse{
        return httpClient.get(TEAM_ENDPOINT)
    }
}
private const val TEAM_ENDPOINT = "https://www.thesportsdb.com/api/v1/json/1/lookup_all_teams.php?id=4328"
