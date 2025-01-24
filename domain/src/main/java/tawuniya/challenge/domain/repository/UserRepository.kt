package tawuniya.challenge.domain.repository

import tawuniya.challenge.domain.entity.UserData

interface UserRepository {
    suspend fun fetchUserData(): Result<List<UserData>?>
    suspend fun likeUser(userId: Long)
    suspend fun unlikeUser(userId: Long)
}