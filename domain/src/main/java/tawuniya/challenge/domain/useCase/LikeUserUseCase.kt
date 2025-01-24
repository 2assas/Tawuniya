package tawuniya.challenge.domain.useCase

import tawuniya.challenge.domain.repository.UserRepository

class LikeUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(userId: Long, isLiked: Boolean) {
        if (userId <= 0) return  // Ignore invalid user ID
        if (isLiked) {
            repository.unlikeUser(userId)
        } else {
            repository.likeUser(userId)
        }
    }
}
