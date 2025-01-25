package tawuniya.challenge.businessCards.userScreen.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import tawuniya.challenge.businessCards.userScreen.UserScreenIntent
import tawuniya.challenge.businessCards.userScreen.UserScreenState
import tawuniya.challenge.businessCards.userScreen.UserViewModel
import tawuniya.challenge.businessCards.utils.WindowInfo
import tawuniya.challenge.businessCards.utils.rememberWindowInfo

@Composable
fun UserList(state: UserScreenState, viewModel: UserViewModel) {
    val windowInfo = rememberWindowInfo()

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        LazyColumn {
            items((state as UserScreenState.Success).users) { user ->
                UserCard(
                    user = user,
                    onToggleLike = { isLiked ->
                        viewModel.handleIntent(UserScreenIntent.ToggleLike(user.id, isLiked))
                    }
                )
            }
        }
    } else {
        LazyVerticalGrid(columns = GridCells.Adaptive(300.dp)) {
            items((state as UserScreenState.Success).users) { user ->
                UserCard(
                    user = user,
                    onToggleLike = { isLiked ->
                        viewModel.handleIntent(UserScreenIntent.ToggleLike(user.id, isLiked))
                    }
                )
            }
        }
    }
}