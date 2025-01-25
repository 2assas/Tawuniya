package tawuniya.challenge.businessCards.userScreen.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import tawuniya.challenge.businessCards.common.ShimmerLoadingScreen
import tawuniya.challenge.businessCards.userScreen.UserScreenIntent
import tawuniya.challenge.businessCards.userScreen.UserScreenState
import tawuniya.challenge.businessCards.userScreen.UserViewModel

@Composable
fun UserScreen(viewModel: UserViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.handleIntent(UserScreenIntent.FetchUsers)
    }
    when (state) {
        is UserScreenState.Loading -> ShimmerLoadingScreen()
        is UserScreenState.Success -> UserList(state, viewModel)
        is UserScreenState.Error -> Text((state as UserScreenState.Error).message)
    }
}


