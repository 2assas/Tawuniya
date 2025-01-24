package tawuniya.challenge.data.dataSource.local

import android.content.Context
import tawuniya.challenge.domain.repository.UserStorage

class LocalStorageImpl(context: Context) : UserStorage {

    private val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    override fun likeUser(userId: Long) {
        val likedUsers = getLikedUsers().toMutableSet()
        likedUsers.add(userId)
        saveLikedUsers(likedUsers)
    }

    override fun unlikeUser(userId: Long) {
        val likedUsers = getLikedUsers().toMutableSet()
        likedUsers.remove(userId)
        saveLikedUsers(likedUsers)
    }

    override fun getLikedUsers(): Set<Long> {
        return sharedPreferences.getStringSet("liked_users", emptySet())
            ?.map { it.toLong() }?.toSet() ?: emptySet()
    }

    private fun saveLikedUsers(userIds: Set<Long>) {
        sharedPreferences.edit()
            .putStringSet("liked_users", userIds.map { it.toString() }.toSet())
            .apply()
    }
}
