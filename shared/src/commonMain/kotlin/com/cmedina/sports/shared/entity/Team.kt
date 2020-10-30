package com.cmedina.sports.shared.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Team(
    @SerialName("idTeam")
    val id : String,
    @SerialName("strTeam")
    val name : String,
    @SerialName("strTeamBadge")
    val badge : String
)

@Serializable
data class TeamResponse(
    @SerialName("teams")
    val teams: List<Team>
)
