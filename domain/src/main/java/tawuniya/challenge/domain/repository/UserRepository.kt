package tawuniya.challenge.domain.repository

import tawuniya.challenge.domain.entity.UserData

interface UserRepository {
    suspend fun fetchUserData(): Result<List<UserData>?>
}