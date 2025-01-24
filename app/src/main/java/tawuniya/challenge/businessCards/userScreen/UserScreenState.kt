package tawuniya.challenge.businessCards.userScreen

import tawuniya.challenge.domain.entity.UserData

sealed class UserScreenState {
    data class Success(val users: List<UserData>) : UserScreenState()
    data class Error(val message: String) : UserScreenState()
    data object Loading : UserScreenState()
}