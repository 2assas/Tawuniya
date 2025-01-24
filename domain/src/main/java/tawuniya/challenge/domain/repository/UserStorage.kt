package tawuniya.challenge.domain.repository

interface UserStorage {
    fun likeUser(userId: Long)
    fun unlikeUser(userId: Long)
    fun getLikedUsers(): Set<Long>
}