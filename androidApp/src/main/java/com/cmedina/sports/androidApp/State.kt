package com.cmedina.sports.androidApp

import com.cmedina.sports.shared.entity.Team

sealed class State<out T> {
    data class Error(val message: String) : State<Nothing>()
    data class Success(val teams : List<Team>) : State<List<Team>>()
    object Loading : State<Nothing>()
}