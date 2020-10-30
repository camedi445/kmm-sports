@file:Suppress("UNCHECKED_CAST")

package com.cmedina.sports.androidApp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cmedina.sports.shared.SportsSDK
import com.cmedina.sports.shared.entity.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class TeamsViewModel(private val sportsSDK: SportsSDK) : ViewModel() {

    val teamsFlow = MutableStateFlow<State<List<Team>>>(State.Loading)

    fun getTeamsData(forceReload: Boolean) {
        teamsFlow.value = State.Loading
        viewModelScope.launch {
            kotlin.runCatching {
                sportsSDK.getTeams(forceReload)
            }.onSuccess {
                teamsFlow.value = State.Success(teams = it)
            }.onFailure {
                teamsFlow.value = State.Error(message = it.localizedMessage ?: API_ERROR_MESSAGE)
            }
        }
    }

}

class TeamsViewModelFactory(
    private val sportsSDK: SportsSDK
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TeamsViewModel(sportsSDK) as T
    }
}

private const val API_ERROR_MESSAGE = "An error occurred retrieving data"