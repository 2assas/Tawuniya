package tawuniya.challenge.businessCards.userScreen

import android.content.Context

sealed class UserScreenIntent {
    data object FetchUsers : UserScreenIntent()
    data class ToggleLike(val userId: Long, val isLiked: Boolean) : UserScreenIntent()
}