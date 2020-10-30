package com.cmedina.sports.androidApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cmedina.sports.shared.SportsSDK
import com.cmedina.sports.shared.cache.DatabaseDriverFactory
import com.cmedina.sports.shared.entity.Team
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.flow.Flow

class MainActivity : AppCompatActivity() {

    private val sdk = SportsSDK(DatabaseDriverFactory(this))
    private val teamViewModel: TeamsViewModel by viewModels{
        TeamsViewModelFactory(sdk)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainLayout(teamsViewModel = teamViewModel)
        }
        teamViewModel.getTeamsData(false)
    }

    @Composable
    fun MainLayout(teamsViewModel: TeamsViewModel) {
        val navController = rememberNavController()
        NavHost(navController, startDestination = SCREEN_TITLE) {
            composable(SCREEN_TITLE) {
                TeamList(teamsFlow = teamsViewModel.teamsFlow)
            }
        }
    }

    @Composable
    fun TeamList(teamsFlow: Flow<State<List<Team>>>) {
        val teamsState = teamsFlow.collectAsState(initial = State.Loading)
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(TITLE) },
                    actions = {
                        Text(
                            text = "Reload",
                            modifier = Modifier.padding(16.dp).clickable(onClick = {
                                teamViewModel.getTeamsData(true)
                            }),
                            color = Color.White
                        )
                    })
            },
            bodyContent = {
                when (teamsState.value) {
                    is State.Loading -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is State.Success -> {
                        LazyColumnFor(items = (teamsState.value as State.Success).teams, itemContent = { team ->
                            TeamView(team = team)
                        })
                    }
                    else -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                           Text((teamsState.value as State.Error).message)
                        }
                    }
                }
            }
        )
    }

    @Composable
    fun TeamView(team: Team) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .then(Modifier.padding(16.dp)), verticalAlignment = Alignment.CenterVertically
        ) {
            CoilImage(data = team.badge, modifier = Modifier.preferredSize(60.dp))
            Spacer(modifier = Modifier.preferredSize(12.dp))

            Column {
                Text(text = team.name, style = TextStyle(fontSize = 20.sp))
            }
        }
    }
}

private const val TITLE = "English Premier"
private const val SCREEN_TITLE = "TeamList"