package tawuniya.challenge.businessCards.userScreen

import android.content.Context

sealed class UserScreenIntent {
    data class FetchUsers(val context: Context) : UserScreenIntent()
    data class ToggleLike(val userId: Long, val isLiked: Boolean) : UserScreenIntent()
}