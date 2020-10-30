package com.cmedina.sports.shared

import com.cmedina.sports.shared.cache.Database
import com.cmedina.sports.shared.cache.DatabaseDriverFactory
import com.cmedina.sports.shared.entity.Team
import com.cmedina.sports.shared.network.SportsApi

class SportsSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = SportsApi()

    @Throws(Exception::class)
    suspend fun getTeams(forceReload: Boolean): List<Team> {
        val cachedTeams = database.getAllTeams()
        return if (cachedTeams.isNotEmpty() && !forceReload) {
            cachedTeams
        } else {
            val response = api.getAllTeams()
            response.teams.also {
                database.clearTeams()
                database.createTeams(it)
            }
        }
    }
}