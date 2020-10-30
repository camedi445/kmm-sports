package com.cmedina.sports.shared.cache

import com.cmedina.sports.shared.entity.Team
import com.cmedina.training.shared.cache.SportsDatabase

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = SportsDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.sportsDatabaseQueries

    internal fun clearTeams() {
        dbQuery.transaction {
            dbQuery.removeAllTeams()
        }
    }

    internal fun getAllTeams(): List<Team> {
        return dbQuery.selectAllTeams(::mapTeam).executeAsList()
    }

    internal fun createTeams(teams: List<Team>) {
        dbQuery.transaction {
            teams.forEach { team ->
                insertTeam(team)
            }
        }
    }

    private fun insertTeam(team: Team) {
        dbQuery.insertTeam(
            id = team.id,
            name = team.name,
            badge = team.badge
        )
    }

    private fun mapTeam(id: String, name: String, badge: String): Team {
        return Team(
            id = id,
            name = name,
            badge = badge
        )
    }
}