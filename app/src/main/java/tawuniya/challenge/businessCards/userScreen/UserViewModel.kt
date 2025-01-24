package tawuniya.challenge.businessCards.userScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tawuniya.challenge.domain.useCase.FetchUserDataUseCase
import tawuniya.challenge.domain.useCase.LikeUserUseCase
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val fetchUserDataUseCase: FetchUserDataUseCase,
    private val likeUserUseCase: LikeUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserScreenState>(UserScreenState.Loading)
    val uiState: StateFlow<UserScreenState> = _uiState

    fun handleIntent(intent: UserScreenIntent) {
        when (intent) {
            is UserScreenIntent.FetchUsers -> fetchUsers()
            is UserScreenIntent.ToggleLike -> toggleLike(intent.userId, intent.isLiked)
        }
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            fetchUserDataUseCase().onSuccess { users ->
                _uiState.value = UserScreenState.Success(users ?: emptyList())
            }.onFailure {
                _uiState.value = UserScreenState.Error("Failed to fetch users")
            }
        }
    }

    private fun toggleLike(userId: Long, isLiked: Boolean) {
        val currentState = _uiState.value
        if (currentState is UserScreenState.Success) {
            val updatedUsers = currentState.users.map { user ->
                if (user.id == userId) user.copy(isLiked = !isLiked) else user
            }
            _uiState.value = UserScreenState.Success(updatedUsers)

            // Perform the actual operation in the background
            viewModelScope.launch {
                try {
                    likeUserUseCase(userId, isLiked)
                } catch (e: Exception) {
                    // Revert the change if the operation fails
                    val revertedUsers = updatedUsers.map { user ->
                        if (user.id == userId) user.copy(isLiked = isLiked) else user
                    }
                    _uiState.value = UserScreenState.Success(revertedUsers)
                }
            }
        }
    }
}
