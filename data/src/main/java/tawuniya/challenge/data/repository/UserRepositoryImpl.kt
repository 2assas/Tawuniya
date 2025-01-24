package tawuniya.challenge.data.repository

import tawuniya.challenge.data.dataSource.remote.ApiService
import tawuniya.challenge.data.mappers.UserDataMapper
import tawuniya.challenge.domain.entity.UserData
import tawuniya.challenge.domain.repository.UserRepository
import tawuniya.challenge.domain.repository.UserStorage

class UserRepositoryImpl(
    private val userApiService: ApiService,
    private val userStorage: UserStorage,
    private val userDataMapper: UserDataMapper
) : UserRepository {

    override suspend fun fetchUserData(): Result<List<UserData>> {
        return try {
            val response = userApiService.getUsers()
            if (response.isSuccessful) {
                val remoteUsers = response.body() ?: emptyList()
                val mappedUsers = userDataMapper.toDomain(remoteUsers)

                // Fetch liked users from local storage
                val likedUserIds = userStorage.getLikedUsers()

                val finalUsers = mappedUsers.map { user ->
                    user.copy(isLiked = likedUserIds.contains(user.id))
                }

                Result.success(finalUsers)
            } else {
                Result.failure(Exception("Failed to fetch data"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun likeUser(userId: Long) {
        userStorage.likeUser(userId)
    }

    override suspend fun unlikeUser(userId: Long) {
        userStorage.unlikeUser(userId)
    }
}
