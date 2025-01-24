package tawuniya.challenge.domain.useCase

import tawuniya.challenge.domain.repository.UserRepository

class LikeUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(userId: Long, isLiked: Boolean) {
        if (isLiked) {
            repository.unlikeUser(userId)
        } else {
            repository.likeUser(userId)
        }
    }
}
